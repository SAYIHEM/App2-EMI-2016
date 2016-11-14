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
import java.util.ArrayList;
import java.util.Iterator;


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
            btnEdit,
            btnShare;
    private EditText
            editTitle,
            editForename,
            editSurname,
            editAdress,
            editCity,
            editZip,
            editCountry;

    private Contacts shownContact = null;
    private ArrayList<Contacts> contactContainer;
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


        // Creating XStream for Converting from/to XML
        final XStream xStream = new XStream();
        xStream.alias("contact", Contacts.class);


        /**** INIT of Buttons etc. ****/

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
        btnShare = (Button) findViewById(R.id.btnShare);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editForename = (EditText) findViewById(R.id.editForename);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editAdress = (EditText) findViewById(R.id.editAdress);
        editCity = (EditText) findViewById(R.id.editCity);
        editZip = (EditText) findViewById(R.id.editZip);
        editCountry = (EditText) findViewById(R.id.editCountry);

        /****   END   ****/

        this.contactContainer = new ArrayList<Contacts>();

        // Check if XML-files exist, loading or creating them
        File file;
        String filePath;
        Contacts[] contactArray = null;

        filePath = "/data/data/de.davidgollasch.emiexercise2/files/contacts.xml";

        file = new File(filePath);
        if (file.exists()) {

            try {

                this.contactContainer = (ArrayList<Contacts>) xStream.fromXML(MainActivity.getStringFromFile(filePath));

                contactArray = new Contacts[this.contactContainer.size()];
                for (int i = 0; i < contactArray.length; i++) {

                    contactArray[i] = this.contactContainer.get(i);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            // Filling the contacts
            Contacts c1 = new Contacts("Herr", "Robert", "Meyer", "Apfelbergstraße 10", "St. Margrethen", "9430", "Schweiz");
            Contacts c2 = new Contacts("Frau", "Elisabeth", "Stramm", "Fritz-Konzert-Straße 1a", "Innsbruck", "6020", "Österreich");
            Contacts c3 = new Contacts("Herr", "Stefan", "Wennige", "Kirchplatz 13", "Wattens", "6112", "Österreich");
            Contacts c4 = new Contacts("Frau", "Ella", "Beckmann", "Falkenstraße 3", "Dresden", "01067", "Deutschland");
            Contacts c5 = new Contacts("Frau", "Anne", "Watson", "1 Langdon Park Rd", "London", "N6 5PS", "Vereinigtes Königreich");

            // Giving contacts a ID
            c1.setID(1);
            c2.setID(2);
            c3.setID(3);
            c4.setID(4);
            c5.setID(5);

            // Filling contactContainer
            this.contactContainer.add(c1);
            this.contactContainer.add(c2);
            this.contactContainer.add(c3);
            this.contactContainer.add(c4);
            this.contactContainer.add(c5);

            /**** Creating XML file ****/

            String xml;
            FileOutputStream fOut;

            xml = xStream.toXML(this.contactContainer);
            fOut = this.openFileOutput("contacts.xml", Context.MODE_PRIVATE);
            fOut.write(xml.getBytes());
            fOut.close();

            /****    END    ****/

            // init and fill array
           contactArray = new Contacts[] {c1, c2, c3, c4, c5};
        }



        //init adapter
        ArrayAdapter<Contacts> adapter = new ArrayAdapter<Contacts>(this, android.R.layout.simple_spinner_item, contactArray);

        // filling
        spContacts.setAdapter(adapter);


        spContacts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(view instanceof TextView))
                    return;

                TextView item = (TextView) view;
                String selectedName = item.getText().toString();

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

                String filePath = "/data/data/de.davidgollasch.emiexercise2/files/contacts.xml";

                try {

                    contactContainer = (ArrayList<Contacts>) xStream.fromXML(getStringFromFile(filePath));

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
                    fOut = MainActivity.this.openFileOutput("contacts.xml", Context.MODE_PRIVATE);
                    fOut.write(xml.getBytes());
                    fOut.close();

                    //System.out.println(xml);

                } catch (Exception e) {
                    e.printStackTrace();
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


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toSend = shownContact.getTitel() + " " + shownContact.toString() + "\n" +
                                shownContact.getAdress() + "\n" +
                                shownContact.getCity() + " " + shownContact.getZip() + "\n" +
                                shownContact.getCountry();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, toSend);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
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
