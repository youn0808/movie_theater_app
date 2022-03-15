package comp3350.sceneit.presentation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class OrderTicketsDialog extends AppCompatDialogFragment {

    private Bundle orderInfoBundle;
    private int selectedRadioButton = 1; //default selection

    public OrderTicketsDialog(Bundle orderInfoBundle) {
        super();
        this.orderInfoBundle = orderInfoBundle;

    }

    @Override
    /**
     * On dialog box creation, should show a dialog box with 2 payment options (credit and paypal).
     * Keeps track of selected option and passes bundle to either payment or credit activity based
     * on choice.
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] items = {"PayPal", "Credit Card"};
        int checkedItem = 1;
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        selectedRadioButton = 0;
                        break;
                    case 1:
                        selectedRadioButton = 1;
                        break;
                }
            }
        });
        builder.setTitle("Please select a payment method:");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (selectedRadioButton == 0) {
                    Intent intent = new Intent(getContext(), PayPalPayment.class);
                    intent.putExtras(orderInfoBundle);
                    startActivity(intent);
                } else if (selectedRadioButton == 1) {
                    Intent intent = new Intent(getContext(), CreditActivity.class);
                    intent.putExtras(orderInfoBundle);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
