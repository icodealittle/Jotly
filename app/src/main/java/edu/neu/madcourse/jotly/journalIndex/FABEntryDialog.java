package edu.neu.madcourse.jotly.journalIndex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import edu.neu.madcourse.jotly.R;

public class FABEntryDialog extends DialogFragment {
    FABEntryDialog.NoticeDialogListener listener;
    private EditText inputName;
    View dialogView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.add_entry_fab, null))
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Get user input
                        inputName = ((AlertDialog)dialog).findViewById(R.id.entryName);
                        String name = inputName.getText().toString();

                        listener.onDialogPositiveClick(FABEntryDialog.this, name);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FABEntryDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FABEntryDialog.NoticeDialogListener) context;
        } catch (ClassCastException e) { // Catch error if the context cannot implement
            throw new ClassCastException("Cannot implement NoticeDialogListener");
        }
    }
}