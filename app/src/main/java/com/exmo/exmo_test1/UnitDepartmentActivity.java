package com.exmo.exmo_test1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exmo.exmo_test1.Entities.Department;
import com.exmo.exmo_test1.Entities.Rating;
import com.exmo.exmo_test1.Parent.NavBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UnitDepartmentActivity extends NavBar {

    ImageView mainImage;
    FloatingActionButton feedBackButton;
    TextView depTitle;
    TextView depDesc;

    ArrayList<String> allDeps,allDescription;
    ArrayList<Integer> allDepsImg;
    String departmentId;


    private DatabaseReference dbSCheduleRef;
    private Department dept;

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_department);
        setTitle("Department Details");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //your code here
        fab=(FloatingActionButton)findViewById(R.id.fab);
        mainImage=(ImageView)findViewById(R.id.mainImage);
        feedBackButton = (FloatingActionButton) findViewById(R.id.feedBack_button);
        depTitle=(TextView) findViewById(R.id.stallName);
        depDesc=(TextView) findViewById(R.id.stallDesc);


        allDeps=new ArrayList<>();
        allDeps.add("Chemical & Process Engineering");
        allDeps.add("Civil Engineering");
        allDeps.add("Computer Science & Engineering");
        allDeps.add("Earth Resource Engineering");
        allDeps.add("Electrical Engineering");
        allDeps.add("Electronics & Telecommunication Engineering");
        allDeps.add("Fashion Design & Product Development");
        allDeps.add("Material Science & Engineering");
        allDeps.add("Mechanical Engineering");
        allDeps.add("Textile & Clothing Technology");
        allDeps.add("Transport & Logistic Management");

        allDescription=new ArrayList<>();
        allDescription.add("Over 36 years, our department strives to educate, conduct research and offer consulting services with dedication, devotion and commitment. We aim to be a place of excellence through internationally recognized programs for the benefit of society. In EXMO 2017, the department is ready to share the technical and innovative ideas and the knowledge of our undergraduates under the sections of Petroleum, Food and Biochemical, Energy and Environmental and Polymer Engineering.");
        allDescription.add("The mission of the Department of Civil Engineering is to develop educational programs that provide professional experiences on the career and improve the innovative abilities of followers in order to come up with inventions to serve the profession, community and nation while being competitive internationally. The department will present many presentations on each section including lab tests for preconstructions and creative representation on results of researches handled successfully and still continuing structure of complex construction centralizing Colombo port, the port city, and also a proud representation on mega police designs on upcoming event EXMO 2017. ");
        allDescription.add("The department produces graduates who work on various innovative products, research and development projects such as Big Data, Machine Learning, Artificial Intelligence, Image and Video Processing. In EXMO 2017, the students of the department will be enlightening visitors with some of the state of the art research products and inventions. ");
        allDescription.add("Department of Earth Resources Engineering, University of Moratuwa, is the primer educational institute for mining engineering in the country. To equip undergraduates with professional level knowledge in a wide range of Earth Resources Engineering fields specially mining and mineral processing, the department provides lab sessions and practical training in 12 laboratories. EXMO 2017 provides the visitors with an opportunity to experience the cutting edge technologies of the mining industry and to acquire latest information on industry trend.");
        allDescription.add("Department of Electrical Engineering commits to produce Electrical Engineering graduates that have been trained to nurture an inquiring mind and have developed skills to face a diversity of challenges with emphasis on national relevance, innovation and creativity. In EXMO 2017 undergraduates will be ready to share their knowledge on innovative, creative projects and technical knowledge in the field of Electrical Engineering.");
        allDescription.add("The Department of Electronic and Telecommunication Engineering moulds the brightest Sri Lankan minds to be innovators and technology leaders throughout the globe. EXMO 2017 will undoubtedly be an incredible experience for the technical enthusiasts to witness the novel projects and latest in technology.\nBiomedical Engineering is also a part of ENTC department. Biomedical Engineering is the application of engineering knowledge to solve challenges in medicine and biology. Research is the corner stone of the sublime field of biomedical engineering. University of Moratuwa stepped into the research area of Biomedical Engineering decades before the introduction of the formal degree program. Many collaborative research partnerships were established with leading medical research bodies of Sri Lanka, resulting in the completion of an important research, leading towards the development of novel and modified products.");
        allDescription.add("The department produces graduates who work on fashion designing and textile designing in leading local and foreign apparel companies and extend their knowledge on business of fashion, trends and sustainable products. In EXMO 2017, the students of the department will be enlightening the visitors with some of the state of the art research products and inventions.");
        allDescription.add("Materials Science and Engineering is a specialty in which the structure-property relationship of materials is studies and modeled in order to gain a good understanding about them. This knowledge is used to improve their performance and to develop new customized materials. The realm of study in this specialty also includes development of material processing methods and designing of materials processing tools.");
        allDescription.add("The Department of Mechanical Engineering brings about graduates who excel in various innovative and development projects in the fields of Mechatronics, Control Systems, Energy Engineering, Manufacturing and Industrial Engineering. EXMO 2017 will provide an ideal platform to showcase the contribution of the department towards uplifting the engineering standards of Sri Lanka and its contribution to the Sri Lankan community.");
        allDescription.add("The vision of the Department of Textile and Clothing Technology is to become a Centre of Excellence in the areas of Textiles, Clothing, Fashion Design and related disciplines through education and knowledge creation. EXMO 2017 will be a platform to showcase the latest technologies used in the Textile industry together with lab tests and ongoing and succeeded projects on areas such as technical textiles, automation, and machinery.");
        allDescription.add("Over the last ten years, Transportation and Logistics and Supply Chain industries have witnessed some interesting transformations due to the ever increasing demands, new consumption patterns, digital age of commerce and innovative mindset. For EXMO 2017, the department is armed with modern and innovative Transportation and Logistics solution models which will solve current industry issues.");


        allDepsImg=new ArrayList<>();
        allDepsImg.add(R.drawable.ch);
        allDepsImg.add(R.drawable.ce);
        allDepsImg.add(R.drawable.cse);
        allDepsImg.add(R.drawable.em);
        allDepsImg.add(R.drawable.ee);
        allDepsImg.add(R.drawable.entc);
        allDepsImg.add(R.drawable.fd);
        allDepsImg.add(R.drawable.mt);
        allDepsImg.add(R.drawable.me);
        allDepsImg.add(R.drawable.tm);
        allDepsImg.add(R.drawable.tlm);
    }



    @Override
    protected void onResume() {
        super.onResume();
        populateAll();
    }

    private void populateAll() {
        //get key from intent
        final String key=getIntent().getStringExtra("DepNum");
        departmentId = key;
        //set title and imae for dep
        Integer img=allDepsImg.get(Integer.parseInt(key));
        mainImage.setImageResource(img);
//        depTitle.setText(allDeps.get(Integer.parseInt(key)));
        depTitle.setText("");
        setTitle(allDeps.get(Integer.parseInt(key)));

        depDesc.setText(allDescription.get(Integer.parseInt(key)));

        //connect db
        dbSCheduleRef = FirebaseDatabase.getInstance().getReference().child("Department").child(key);

        dbSCheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dept = dataSnapshot.getValue(Department.class);



                feedBackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //pop up to ask all details
                        LayoutInflater layoutInflater = LayoutInflater.from(UnitDepartmentActivity.this);
                        View promptView = layoutInflater.inflate(R.layout.feedback_department_popup, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UnitDepartmentActivity.this);
                        alertDialogBuilder.setView(promptView);

                        final EditText emailEditText=(EditText)promptView.findViewById(R.id.editText_email);
                        final EditText feedbackEditText=(EditText)promptView.findViewById(R.id.editText_feedback);
                        final RatingBar ratingNew=(RatingBar)promptView.findViewById(R.id.ratingBar2);

                        alertDialogBuilder.setCancelable(true)
                                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(!emailEditText.getText().toString().contains("@")){
                                            Toast.makeText(UnitDepartmentActivity.this, "Incorrect Email Address", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        String emailAddress=emailEditText.getText().toString();
                                        String feedback=feedbackEditText.getText().toString();
                                        float ratingValue=ratingNew.getRating();

                                        Rating rating = new Rating(emailAddress,feedback,ratingValue);

                                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().
                                                child("departmentRating").child(departmentId);
                                        String pushKey = dbRef.push().getKey();
                                        dbRef.child(pushKey).setValue(rating);

                                        Toast.makeText(UnitDepartmentActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Back",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }
                });

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent d=new Intent(UnitDepartmentActivity.this,MapActivity.class);
                        d.putExtra("KeyDept",key);
                        Log.e("unit dept actvit",key);
                        startActivity(d);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
