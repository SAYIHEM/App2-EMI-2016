package de.davidgollasch.emiexercise2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thoughtworks.xstream.XStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;


public class EditContactActivity extends AppCompatActivity {

    private Contacts contact;

    private EditText
            editTitle,
            editForename,
            editSurname,
            editAdress,
            editCity,
            editZip,
            editCountry;
    private Button
            btnSave;
    private String oldName;

    private boolean debug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        contact = (Contacts) getIntent().getSerializableExtra("contact");
        oldName = contact.toString();

        final XStream xStream = new XStream();
        xStream.alias("contact", Contacts.class);

        InitializeActivity();
    }

    private void InitializeActivity() {

        editTitle = (EditText) findViewById(R.id.editTitle);
        editForename = (EditText) findViewById(R.id.editForename);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editAdress = (EditText) findViewById(R.id.editAdress);
        editCity = (EditText) findViewById(R.id.editCity);
        editZip = (EditText) findViewById(R.id.editZip);
        editCountry = (EditText) findViewById(R.id.editCountry);

        btnSave = (Button) findViewById(R.id.btnSave);

        editTitle.setText(contact.getTitel());
        editForename.setText(contact.getForename());
        editSurname.setText(contact.getSurname());
        editAdress.setText(contact.getAdress());
        editZip.setText(contact.getZip());
        editCity.setText(contact.getCity());
        editCountry.setText(contact.getCountry());

        final XStream xStream = new XStream();
        xStream.alias("contact", Contacts.class);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contact.setTitel(editTitle.getText().toString());
                contact.setForename(editForename.getText().toString());
                contact.setSurname(editSurname.getText().toString());
                contact.setAdress(editAdress.getText().toString());
                contact.setCity(editCity.getText().toString());
                contact.setZip(editZip.getText().toString());
                contact.setCountry(editCountry.getText().toString());


                /**** Saving Contact to XML ****/

                String fileName;
                String filePath;

                for (int i = 1; i <= 5; i++) {

                    fileName = "c" + Integer.toString(i) + ".xml";
                    filePath = "/data/data/de.davidgollasch.emiexercise2/files/" + "c" + Integer.toString(i) + ".xml";

                    try {

                        Contacts parsedContact = (Contacts) xStream.fromXML(MainActivity.getStringFromFile(filePath));

                        if (parsedContact.getID() == contact.getID()) {

                            String xml;
                            FileOutputStream fOut;

                            xml = xStream.toXML(contact);
                            fOut = EditContactActivity.this.openFileOutput(fileName, Context.MODE_PRIVATE);
                            fOut.write(xml.getBytes());
                            fOut.close();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /**** END ****/

                // Sending back Contact
                Intent resultIntent = new Intent();

                resultIntent.putExtra("contact", contact);
                resultIntent.putExtra("oldName", oldName);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

        });
    }




}
