package com.tfg.vladimirvatsurinmidterm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_order.*

class MainActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b_order.setOnClickListener {
            /**
             * var name = et_Name.text.toString().trim();

            if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Artist Name is Required", Toast.LENGTH_LONG);
            } else {
            val genre = spinner.selectedItem.toString();

            val tbl = db.collection("artists")
            val artistId = tbl.document().id;

            val artist = Artist(artistId,name,genre);

            tbl.document(artistId).set(artist);

            et_Name.setText("");
            spinner.setSelection(0);
            Toast.makeText(this, "Artist Added", Toast.LENGTH_SHORT);
            }
             */

            var firstName = et_FName.text.toString();
            var lastName = et_LName.text.toString();
            if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)){
                Toast.makeText(this, "First and Last Name are Required", Toast.LENGTH_LONG);
            }else{
                var tbl = db.collection("orders")
                var quantity =  sp_Quantity.selectedItem.toString().toInt();
                var total: Int = 1;
                if(sp_Choice.selectedItem.toString() == "Small"){
                    total = quantity * 10;
                }else if(sp_Choice.selectedItem.toString() == "Medium"){
                    total = quantity * 15;
                }else if(sp_Choice.selectedItem.toString() == "Large"){
                    total = quantity * 20;
                }
                var order = Order(
                    tbl.document().id,
                    firstName,
                    lastName,
                    sp_Type.selectedItem.toString(),
                    sp_Choice.selectedItem.toString(),
                    quantity,
                    total
                );
                tbl.document(order.id).set(order);
                Toast.makeText(this, "Order is placed! Your total is $$total", Toast.LENGTH_SHORT);
            }
        }

        b_view.setOnClickListener {
            val intent =  Intent(this.applicationContext, ListActivity::class.java)
            startActivity(intent);
        }

        b_reset.setOnClickListener {
            et_FName.setText("")
            et_LName.setText("")

            sp_Choice.setSelection(0);
            sp_Type.setSelection(0);
            sp_Quantity.setSelection(0)
        }
    }


}
