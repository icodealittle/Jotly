package edu.neu.madcourse.jotly.journalIndex;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.jotly.HomePageActivity;
import edu.neu.madcourse.jotly.Location;
import edu.neu.madcourse.jotly.R;

public class FABEntryDialog extends DialogFragment implements LocationListener {
    FABEntryDialog.NoticeDialogListener listener;
    LocationManager locationManager;
    String name, content, checkedLocation;
    private EditText inputName, inputContent;
    private CheckBox locationCB;
    private Boolean hasLocation = false;
    View dialogView;
    private AppCompatActivity context;

    public FABEntryDialog(AppCompatActivity context){
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        checkedLocation = "NA";
        View view = inflater.inflate(R.layout.entry_activity, null);

        locationCB =  view.findViewById(R.id.locationCB);
        locationCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasLocation = locationCB.isChecked();
                if (hasLocation) {
                    //Log.e("This is checked", "Show here");
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(context, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, 100);
//                        Log.e("This is permited", "Show here");
                    }
                }
            }
        });
        builder.setView(view)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Get user input
                        inputName = ((AlertDialog)dialog).findViewById(R.id.inputName);
                        inputContent = ((AlertDialog)dialog).findViewById(R.id.inputContent);
                        locationCB = ((AlertDialog)dialog).findViewById(R.id.locationCB);
                        name = inputName.getText().toString();
                            content = inputContent.getText().toString();
                            if (hasLocation) {
                                getLocation();
                            } else {
                                listener.onDialogPositiveClick(FABEntryDialog.this, name, content, checkedLocation);
                            }
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
        public void onDialogPositiveClick(DialogFragment dialog, String name, String content, String checkedLocation);
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

    // send get location request
    @SuppressLint("MissingPermission")
    private void getLocation() {
        //Log.e("This gets into gl", "Show here");
        try {
            locationManager = (LocationManager) context.getApplicationContext()
                    .getSystemService(context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,
                    5, FABEntryDialog.this);
            //Log.e("This gets into gl try", "Show here");
        } catch (Exception exception) {
            exception.printStackTrace();
            //Log.e("This gets into gl exc", "Show here");
            Toast.makeText(context, "Unable to create the journal with location without permission",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
//        Log.e("This gets into ol", "Show here");
        try {
//            Log.e("This gets into ol try", "Show here");
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
//            Log.e("This is" + address, "Show here");

            checkedLocation = address;
            listener.onDialogPositiveClick(FABEntryDialog.this, name, content, checkedLocation);
//            Log.e("This is " + checkedLocation, "Show here");
        } catch (Exception e) {
            Log.e("This gets into ol exp", "Show here");
            e.printStackTrace();
            Toast.makeText(context, "Unable to create entry with location without permission",
                    Toast.LENGTH_SHORT).show();
            //TODO when no permission for location  app crush
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<android.location.Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}