package de.davidgollasch.emiexercise2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.*;
import android.widget.ArrayAdapter;

import com.thoughtworks.xstream.XStream;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.CharBuffer;


public class MainActivity extends AppCompatActivity {

    private Spinner spContacts;
    private TextView
            tvTitle,
            tvFirstName,
            tvLastName,
            tvAddress,
            tvZip,
            tvCity,
            tvCountry;
    private Button
            btnEditHere,
            btnSave,
            btnEdit;
    private EditText
            editTitle,
            editForename,
            editSurname,
            editAdress,
            editCity,
            editZip,
            editCountry;

    private Contacts shownContact = null;
    private String oldContactName;

    private boolean debug = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.tuHausfarbeBlauDunkel)));

        try {
            InitializeApp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Construct the Interactive Structure
     */
    private void InitializeApp() throws IOException {

        final XStream xStream = new XStream();
        xStream.alias("contact", Contacts.class);




        spContacts = (Spinner) findViewById(R.id.spinnerContacts);

        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvFirstName = (TextView) findViewById(R.id.textViewFirstName);
        tvLastName = (TextView) findViewById(R.id.textViewLastName);
        tvAddress = (TextView) findViewById(R.id.textViewAddress);
        tvZip = (TextView) findViewById(R.id.textViewZip);
        tvCity = (TextView) findViewById(R.id.textViewCity);
        tvCountry = (TextView) findViewById(R.id.textViewCountry);


        btnEditHere = (Button) findViewById(R.id.btnEditHere);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editForename = (EditText) findViewById(R.id.editForename);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editAdress = (EditText) findViewById(R.id.editAdress);
        editCity = (EditText) findViewById(R.id.editCity);
        editZip = (EditText) findViewById(R.id.editZip);
        editCountry = (EditText) findViewById(R.id.editCountry);

        Contacts c1 = null;
        Contacts c2 = null;
        Contacts c3 = null;
        Contacts c4 = null;
        Contacts c5 = null;


        File file;
        String fileName, filePath;
        for (int i = 1; i <= 5; i++) {

            fileName = "c" + Integer.toString(i) + ".xml";
            filePath = "/data/data/de.davidgollasch.emiexercise2/files/" + "c" + Integer.toString(i) + ".xml";

            file = new File(filePath);

            if (file.exists()) {

                try {

                    switch (i) {

                        case(1): c1 = (Contacts) xStream.fromXML(MainActivity.getStringFromFile(filePath));
                        case(2): c2 = (Contacts) xStream.fromXML(MainActivity.getStringFromFile(filePath));
                        case(3): c3 = (Contacts) xStream.fromXML(MainActivity.getStringFromFile(filePath));
                        case(4): c4 = (Contacts) xStream.fromXML(MainActivity.getStringFromFile(filePath));
                        case(5): c5 = (Contacts) xStream.fromXML(MainActivity.getStringFromFile(filePath));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                // Filling the contacts
                c1 = new Contacts("Herr", "Robert", "Meyer", "Apfelbergstraße 10", "St. Margrethen", "9430", "Schweiz");
                c2 = new Contacts("Frau", "Elisabeth", "Stramm", "Fritz-Konzert-Straße 1a", "Innsbruck", "6020", "Österreich");
                c3 = new Contacts("Herr", "Stefan", "Wennige", "Kirchplatz 13", "Wattens", "6112", "Österreich");
                c4 = new Contacts("Frau", "Ella", "Beckmann", "Falkenstraße 3", "Dresden", "01067", "Deutschland");
                c5 = new Contacts("Frau", "Anne", "Watson", "1 Langdon Park Rd", "London", "N6 5PS", "Vereinigtes Königreich");

                // Giving contacts a ID
                c1.setID(1);
                c2.setID(2);
                c3.setID(3);
                c4.setID(4);
                c5.setID(5);

                /**** Creating XML files ****/

                String xml;
                FileOutputStream fOut;

                xml = xStream.toXML(c1);
                fOut = this.openFileOutput("c1.xml", Context.MODE_PRIVATE);
                fOut.write(xml.getBytes());
                fOut.close();

                xml = xStream.toXML(c2);
                fOut = this.openFileOutput("c2.xml", Context.MODE_PRIVATE);
                fOut.write(xml.getBytes());
                fOut.close();

                xml = xStream.toXML(c3);
                fOut = this.openFileOutput("c3.xml", Context.MODE_PRIVATE);
                fOut.write(xml.getBytes());
                fOut.close();

                xml = xStream.toXML(c4);
                fOut = this.openFileOutput("c4.xml", Context.MODE_PRIVATE);
                fOut.write(xml.getBytes());
                fOut.close();

                xml = xStream.toXML(c5);
                fOut = this.openFileOutput("c5.xml", Context.MODE_PRIVATE);
                fOut.write(xml.getBytes());
                fOut.close();

                /**** END ****/
            }
        }


        // init and fill array
        Contacts[] contactArray = new Contacts[] {c1, c2, c3, c4, c5};

        //init adapter
        ArrayAdapter<Contacts> adapter = new ArrayAdapter<Contacts>(this, android.R.layout.simple_spinner_item, contactArray);

        // filling
        spContacts.setAdapter(adapter);


        /*
        Herr Robert Meyer       Frau Elisabeth Stramm       Herr Stefan Wennige
        Apfelbergstraße 10      Fritz-Konzert-Straße 1a     Kirchplatz 13
        9430 St. Margrethen     6020 Innsbruck              6112 Wattens
        Schweiz                 Österreich                  Österreich


        Frau Ella Beckmann      Frau Anne Watson
        Falkenstraße 3          1 Langdon Park Rd
        01067 Dresden           London N6 5PS
        Deutschland             Vereinigtes Königreich
        */


        spContacts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(view instanceof TextView))
                    return;

                TextView item = (TextView) view;
                String selectedName = item.getText().toString();

                /*

                Let's show the contact's details:
                 */
                DisplayContactDetails(selectedName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });


        btnEditHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTitle.setText(shownContact.getTitel());
                editForename.setText(shownContact.getForename());
                editSurname.setText(shownContact.getSurname());
                editAdress.setText(shownContact.getAdress());
                editZip.setText(shownContact.getZip());
                editCity.setText(shownContact.getCity());
                editCountry.setText(shownContact.getCountry());

                editTitle.setVisibility(View.VISIBLE);
                editForename.setVisibility(View.VISIBLE);
                editSurname.setVisibility(View.VISIBLE);
                editAdress.setVisibility(View.VISIBLE);
                editZip.setVisibility(View.VISIBLE);
                editCity.setVisibility(View.VISIBLE);
                editCountry.setVisibility(View.VISIBLE);

                btnSave.setVisibility(View.VISIBLE);

                btnEdit.setEnabled(false);
                btnEditHere.setEnabled(false);
                spContacts.setEnabled(false);

                btnSave.setEnabled(true);

            }
        });

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

                tvTitle.setText(shownContact.getTitel());
                tvFirstName.setText(shownContact.getForename());
                tvLastName.setText(shownContact.getSurname());
                tvAddress.setText(shownContact.getAdress());
                tvCity.setText(shownContact.getCity());
                tvZip.setText(shownContact.getZip());
                tvCountry.setText(shownContact.getCountry());

                editTitle.setVisibility(View.INVISIBLE);
                editForename.setVisibility(View.INVISIBLE);
                editSurname.setVisibility(View.INVISIBLE);
                editAdress.setVisibility(View.INVISIBLE);
                editZip.setVisibility(View.INVISIBLE);
                editCity.setVisibility(View.INVISIBLE);
                editCountry.setVisibility(View.INVISIBLE);

                btnSave.setVisibility(View.INVISIBLE);

                btnEdit.setEnabled(true);
                btnEditHere.setEnabled(true);
                spContacts.setEnabled(true);

                /**** Saving Contact to XML ****/

                String fileName;
                String filePath;

                for (int i = 1; i <= 5; i++) {

                    fileName = "c" + Integer.toString(i) + ".xml";
                    filePath = "/data/data/de.davidgollasch.emiexercise2/files/" + "c" + Integer.toString(i) + ".xml";

                    try {

                        Contacts parsedContact = (Contacts) xStream.fromXML(getStringFromFile(filePath));

                        if (parsedContact.getID() == shownContact.getID()) {


                            String xml;
                            FileOutputStream fOut;

                            xml = xStream.toXML(shownContact);
                            fOut = MainActivity.this.openFileOutput(fileName, Context.MODE_PRIVATE);
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

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startEditContactActivity();
            }
        });
    }

    /**
     * Set the displayed contact details by contact name
     * @param contactName contact's name
     */
    private void DisplayContactDetails(String contactName) {

        // init
        int i;
        Contacts contact;

        // get adapter
        SpinnerAdapter adapter = spContacts.getAdapter();

        // get contact by "contactName
        for (i = 0; i < adapter.getCount(); i++) {

            contact = (Contacts) adapter.getItem(i);

            // if found contact: fill the text-fields
            if (contact.toString().equals(contactName)) {

                tvTitle.setText(contact.getTitel());
                tvFirstName.setText(contact.getForename());
                tvLastName.setText(contact.getSurname());
                tvAddress.setText(contact.getAdress());
                tvZip.setText(contact.getZip());
                tvCity.setText(contact.getCity());
                tvCountry.setText(contact.getCountry());

                shownContact = contact;

                break;
            }
        }
    }

    static final int EDIT_CONTACT = 1;

    // Starting EditContact Activity
    private void startEditContactActivity() {

        Intent i = new Intent(this, EditContactActivity.class);
        i.putExtra("contact", shownContact);

        startActivityForResult(i, EDIT_CONTACT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDIT_CONTACT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                shownContact = (Contacts) data.getSerializableExtra("contact");
                oldContactName = (String) data.getSerializableExtra("oldName");

                int i;
                SpinnerAdapter adapter = spContacts.getAdapter();


                Contacts contactArray[] = new Contacts[adapter.getCount()];

                for (i = 0; i < adapter.getCount(); i++) {

                    contactArray[i] = (Contacts) adapter.getItem(i);

                    if (contactArray[i].toString().equals(oldContactName)) {

                        contactArray[i] = shownContact;
                    }
                }

                ArrayAdapter<Contacts> arrayAdapter = new ArrayAdapter<Contacts>(this, android.R.layout.simple_spinner_item, contactArray);

                spContacts.setAdapter(arrayAdapter);
                spContacts.setSelection(arrayAdapter.getPosition(shownContact));
            }
        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }
}
