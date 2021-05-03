package sg.edu.rp.c346.id16014507.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {
    EditText etAmount;
    EditText etPax;
    ToggleButton tbtnSVS;
    ToggleButton tbtnGST;
    EditText etDiscount;
    RadioGroup rgPaymentMethod;
    Button btnSplit;
    Button btnReset;
    TextView tvBill;
    TextView tvPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.AmountDecimal);
        etPax = findViewById(R.id.NumPax);
        tbtnSVS = findViewById(R.id.toggleSVS);
        tbtnGST = findViewById(R.id.toggleGST);
        etDiscount = findViewById(R.id.Discount);
        rgPaymentMethod = findViewById(R.id.RadioGroupPaymentMethod);
        btnSplit = findViewById(R.id.buttonSplit);
        btnReset = findViewById(R.id.buttonReset);
        tvBill = findViewById(R.id.totalBill);
        tvPay = findViewById(R.id.eachPay);

        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAmount.getText().toString().trim().length() != 0 && etPax.getText().toString().trim().length() != 0) {
                    double newAmount = 0.0;
                    if (!tbtnSVS.isChecked() && !tbtnGST.isChecked()) {
                        newAmount = Double.parseDouble(etAmount.getText().toString());
                    } else if (tbtnSVS.isChecked() && !tbtnGST.isChecked()) {
                        newAmount = Double.parseDouble(etAmount.getText().toString()) * 1.1;
                    } else if (!tbtnSVS.isChecked() && tbtnGST.isChecked()) {
                        newAmount = Double.parseDouble(etAmount.getText().toString()) * 1.07;
                    } else {
                        newAmount = Double.parseDouble(etAmount.getText().toString()) * 1.17;
                    }
                    if (etDiscount.getText().toString().trim().length() != 0) {
                        newAmount = newAmount - (newAmount * (Double.parseDouble(etDiscount.getText().toString()) / 100));
                    }
                    tvBill.setText("Total Bill: $ " + String.format("%.2f", newAmount));
                    int numberPax = Integer.parseInt(etPax.getText().toString());
                    if (numberPax != 0) {
                        if (rgPaymentMethod.getCheckedRadioButtonId() == R.id.radioCash) {
                            tvPay.setText("Each Pays: $" +String.format("%.2f", newAmount/numberPax) + " in cash");
                        } else if (rgPaymentMethod.getCheckedRadioButtonId() == R.id.radioPayNow) {
                            tvPay.setText("Each Pays: $" +String.format("%.2f", newAmount/numberPax) + " via PayNow to 912345678");
                        }
                    }
                } else {
                    tvBill.setText("Invalid");
                    tvPay.setText("");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAmount.setText("");
                etPax.setText("");
                tbtnSVS.setChecked(false);
                tbtnGST.setChecked(false);
                etDiscount.setText("");
                rgPaymentMethod.check(R.id.radioCash);
            }
        });
    }
}