"""
CLI Integration tests for PricingEngine
========================================
Spawns the Java PricingEngine as a subprocess and asserts against its
printed invoice output.

"""

import subprocess
import pytest

CLASSPATH = "app/build/classes/java/main"


def run_pricing(prices: list, quantities: list, customer_type: str, promo: str | None = None) -> str:
    """Invoke app.Main as a subprocess and return its stdout."""
    cmd = [
        "java", "-cp", CLASSPATH, "app.Main",
        ",".join(map(str, prices)),
        ",".join(map(str, quantities)),
        customer_type,
        str(promo) if promo else "null",
    ]
    result = subprocess.run(cmd, capture_output=True, text=True)
    assert result.returncode == 0, f"Java process failed:\n{result.stderr}"
    return result.stdout


class TestPricingIntegration:

    # REGULAR customer
    def test_regular_no_discount(self):
        # 100 × 1 = 100 | 0% discount | tax $8 | final 108.0
        output = run_pricing([100.0], [1], "REGULAR")
        assert "Subtotal: 100.0" in output
        assert "Discount: 0.0"   in output
        assert "Tax: 8.0"        in output
        assert "Final: 108.0"    in output

    def test_regular_save10(self):
        # 100 × 1 = 100 | 10% = $10 off | subtotal 90 | tax $7.2 | final 97.2
        output = run_pricing([100.0], [1], "REGULAR", "SAVE10")
        assert "Discount: 10.0" in output
        assert "Final: 97.2"    in output

    def test_regular_save20(self):
        # 100 × 1 = 100 | 20% = $20 off | subtotal 80 | tax $6.4 | final 86.4
        output = run_pricing([100.0], [1], "REGULAR", "SAVE20")
        assert "Discount: 20.0" in output
        assert "Final: 86.4"    in output


    # VIP customer
    def test_vip_no_promo(self):
        # 100 × 1 = 100 | 15% = $15 off | subtotal 85 | tax $6.8 | final 91.8
        output = run_pricing([100.0], [1], "VIP")
        assert "Discount: 15.0" in output
        assert "Final: 91.8"    in output

    def test_vip_save10(self):
        # VIP 15% + SAVE10 10% = 25% | $25 off | subtotal 75 | tax $6 | final 81.0
        output = run_pricing([100.0], [1], "VIP", "SAVE10")
        assert "Discount: 25.0" in output
        assert "Final: 81.0"    in output

    def test_vip_save20(self):
        # VIP 15% + SAVE20 20% = 35% | $35 off | subtotal 65 | tax $5.2 | final 70.2
        output = run_pricing([100.0], [1], "VIP", "SAVE20")
        assert "Discount: 35.0" in output
        assert "Final: 70.2"    in output


    # Multi-item order
    def test_total_summation_multiple_items(self):
        # 50×2=100, 25×2=50 → total 150 | REGULAR no promo | tax $12 | final 162.0
        output = run_pricing([50.0, 25.0], [2, 2], "REGULAR")
        assert "Subtotal: 150.0" in output
        assert "Discount: 0.0"   in output
        assert "Final: 162.0"    in output


    # Edge cases 
    def test_empty_product_list(self):
        # No items
        output = run_pricing([], [], "REGULAR")
        assert "Subtotal: 0.0" in output
        assert "Discount: 0.0" in output
        assert "Final: 0.0"    in output

    def test_null_promo_code(self):
        # Explicit null behaves the same as no promo for REGULAR
        output = run_pricing([100.0], [1], "REGULAR", None)
        assert "Discount: 0.0" in output
        assert "Final: 108.0"  in output

    def test_invoice_header_always_present(self):
        # The invoice block should always be printed
        output = run_pricing([100.0], [1], "REGULAR")
        assert "--- Invoice ---" in output

    def test_tax_applied_after_discount(self):
        # Tax must be on the post-discount subtotal, not the gross total.
        # 1000 | REGULAR SAVE20 → 20% off = $200 | subtotal 800 | tax $64 | final 864.0
        output = run_pricing([1000.0], [1], "REGULAR", "SAVE20")
        assert "Final: 864.0" in output


if __name__ == "__main__":
    pytest.main([__file__, "-v"])