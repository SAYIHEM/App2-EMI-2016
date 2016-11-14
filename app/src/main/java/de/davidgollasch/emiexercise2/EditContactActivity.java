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
import java.util.ArrayList;


public class EditContactActivity extends AppCompatActivity {

    private Contacts shownContact;

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
    private ArrayList<Contacts> contactContainer;

    private boolean debug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        shownContact = (Contacts) getIntent().getSerializableExtra("contact");
        oldName = shownContact.toString();

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

        editTitle.setText(shownContact.getTitel());
        editForename.setText(shownContact.getForename());
        editSurname.setText(shownContact.getSurname());
        editAdress.setText(shownContact.getAdress());
        editZip.setText(shownContact.getZip());
        editCity.setText(shownContact.getCity());
        editCountry.setText(shownContact.getCountry());

        final XStream xStream = new XStream();
        xStream.alias("contact", Contacts.class);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shownContact.setTitel(editTitle.getText().toString());
                shownContact.setForename(editForename.getText().toString());
                shownContact.setSurname(editSurname.getText().toString());
                shownContact.setAdress(editAdress.getText().toString());
                shownContact.setCity(editCity.getText().toString());
                shownContact.setZip(editZip.getText().toString());
                shownContact.setCountry(editCountry.getText().toString());


                /**** Saving Contact to XML ****/

                String filePath = "/data/data/de.davidgollasch.emiexercise2/files/contacts.xml";

                try {

                    contactContainer = (ArrayList<Contacts>) xStream.fromXML(MainActivity.getStringFromFile(filePath));

                    ArrayList<Contacts> clonedContainer = (ArrayList<Contacts>) ((ArrayList<Contacts>) contactContainer).clone();


                    contactContainer.clear();

                    for (int i = 0; i < clonedContainer.size(); i++) {

                        if (clonedContainer.get(i).getID() == shownContact.getID()) {

                            contactContainer.add(shownContact);

                        } else {

                            contactContainer.add(clonedContainer.get(i));
                        }
                    }

                    String xml;
                    FileOutputStream fOut;

                    xml = xStream.toXML(contactContainer);
                    fOut = EditContactActivity.this.openFileOutput("contacts.xml", Context.MODE_PRIVATE);
                    fOut.write(xml.getBytes());
                    fOut.close();

                    //System.out.println(xml);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**** END ****/


                // Sending back Contact
                Intent resultIntent = new Intent();

                resultIntent.putExtra("contact", shownContact);
                resultIntent.putExtra("oldName", oldName);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

        });
    }




}
