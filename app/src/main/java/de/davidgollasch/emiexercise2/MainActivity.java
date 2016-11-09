package de.davidgollasch.emiexercise2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.ArrayAdapter;

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

    private String actualContact;
    private Contacts shownContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.tuHausfarbeBlauDunkel)));

        InitializeApp();
    }

    /**
     * Construct the Interactive Structure
     */
    private void InitializeApp() {
        spContacts = (Spinner) findViewById(R.id.spinnerContacts);

        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvFirstName = (TextView) findViewById(R.id.textViewFirstName);
        tvLastName = (TextView) findViewById(R.id.textViewLastName);
        tvAddress = (TextView) findViewById(R.id.textViewAddress);
        tvZip = (TextView) findViewById(R.id.textViewZip);
        tvCity = (TextView) findViewById(R.id.textViewCity);
        tvCountry = (TextView) findViewById(R.id.textViewCountry);

        /*
        TODO: (TASK 2.2 and 3.3) Bind required new UI widgets (EditTexts and Buttons) here
         */
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



        // Filling the contacts
        Contacts c1 = new Contacts("Herr", "Robert", "Meyer", "Apfelbergstraße 10", "St. Margrethen", "9430", "Schweiz");
        Contacts c2 = new Contacts("Frau", "Elisabeth", "Stramm", "Fritz-Konzert-Straße 1a", "Innsbruck", "6020", "Österreich");
        Contacts c3 = new Contacts("Herr", "Stefan", "Wennige", "Kirchplatz 13", "Wattens", "6112", "Österreich");
        Contacts c4 = new Contacts("Frau", "Ella", "Beckmann", "Falkenstraße 3", "Dresden", "01067", "Deutschland");
        Contacts c5 = new Contacts("Frau", "Anne", "Watson", "1 Langdon Park Rd", "London", "N6 5PS", "Vereinigtes Königreich");

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

                actualContact = selectedName;

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

                // init
                int i;
                String name;
                Contacts contact;

                // get adapter
                SpinnerAdapter adapter = spContacts.getAdapter();

                // get contact by "contactName
                for (i = 0; i < adapter.getCount(); i++) {

                    contact = (Contacts) adapter.getItem(i);

                    name = contact.getForename() + " " + contact.getSurname();

                    // if found contact: fill the edit-fields / view the edit-fields
                    if (name.equals(actualContact)) {


                        // TODO: Umbauen nach shownContact!!!

                        editTitle.setText(contact.getTitel());
                        editForename.setText(contact.getForename());
                        editSurname.setText(contact.getSurname());
                        editAdress.setText(contact.getAdress());
                        editZip.setText(contact.getZip());
                        editCity.setText(contact.getCity());
                        editCountry.setText(contact.getCountry());

                        editTitle.setVisibility(View.VISIBLE);
                        editForename.setVisibility(View.VISIBLE);
                        editSurname.setVisibility(View.VISIBLE);
                        editAdress.setVisibility(View.VISIBLE);
                        editZip.setVisibility(View.VISIBLE);
                        editCity.setVisibility(View.VISIBLE);
                        editCountry.setVisibility(View.VISIBLE);

                        btnSave.setVisibility(View.VISIBLE);

                        break;
                    }
                }
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
        String name;
        Contacts contact;

        // get adapter
        SpinnerAdapter adapter = spContacts.getAdapter();

        // get contact by "contactName
        for (i = 0; i < adapter.getCount(); i++) {

            contact = (Contacts) adapter.getItem(i);

            name = contact.getForename() + " " + contact.getSurname();

            // if found contact: fill the text-fields
            if (name.equals(contactName)) {

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

    // Starting EditContact Activity
    private void startEditContactActivity() {

        System.out.println("test");

        startActivity(
                new Intent(this, EditContactActivity.class));

    }
}
