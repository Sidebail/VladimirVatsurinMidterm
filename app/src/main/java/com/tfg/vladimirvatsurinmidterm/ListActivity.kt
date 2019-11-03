package com.tfg.vladimirvatsurinmidterm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.item_order.*

class ListActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance();

    private var orderList = mutableListOf<Order>();

    var chosenOrder = Order();
    var chosenLayout:LinearLayout? = null;

    private var adapter: OrderAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        rv_List.setLayoutManager(LinearLayoutManager(this));

        val query = db.collection("orders").orderBy("firstName", Query.Direction.ASCENDING);

        val options = FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order::class.java).build();

        adapter = OrderAdapter(options);

        rv_List.adapter = adapter

        b_delete.setOnClickListener {
            db.collection("orders").document(chosenOrder.id).delete()
            b_delete.isVisible = false
            chosenLayout = null
            chosenOrder = Order()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening();
    }

    override fun onStop(){
        super.onStop();
        adapter!!.stopListening()
    }

    inner class OrderViewHolder internal constructor(private val view: View): RecyclerView.ViewHolder(view){

        /**
        internal fun displayArtist(artistName: String, artistGenre: String){
        val textViewName = view.findViewById<TextView>(R.id.tv_name)
        val textViewGenre = view.findViewById<TextView>(R.id.tv_genre)

        textViewGenre.text = artistGenre;
        textViewName.text = artistName;

        }
         */
    }

    private inner class OrderAdapter internal constructor(options: FirestoreRecyclerOptions<Order>) : FirestoreRecyclerAdapter<Order,
            OrderViewHolder>(options){
        override fun onBindViewHolder(p0: OrderViewHolder, p1: Int, p2: Order) {
            p0.itemView.findViewById<TextView>(R.id.tv_FirstName).text = p2.firstName;
            p0.itemView.findViewById<TextView>(R.id.tv_LastName).text = p2.lastName;
            p0.itemView.findViewById<TextView>(R.id.tv_Type).text = p2.type + " Pizza";
            p0.itemView.findViewById<TextView>(R.id.tv_Size).text = "Size: " +  p2.size;
            p0.itemView.findViewById<TextView>(R.id.tv_Quantity).text = "Quantity: " +  p2.quantity;
            p0.itemView.findViewById<TextView>(R.id.tv_Total).text = "Total: $" + p2.total;

            p0.itemView.findViewById<LinearLayout>(R.id.ll_background).setOnClickListener {
                p0.itemView.findViewById<LinearLayout>(R.id.ll_background).setBackgroundColor(resources.getColor(R.color.colorPrimary))
                if(chosenLayout == null){
                    //chosenLayout!!.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    chosenLayout = p0.itemView.findViewById<LinearLayout>(R.id.ll_background)
                    chosenOrder = p2;
                    b_delete.isVisible = true
                }else if (chosenLayout == p0.itemView.findViewById<LinearLayout>(R.id.ll_background)){
                    chosenLayout!!.setBackgroundColor(resources.getColor(R.color.colorWhite))
                    chosenLayout = null
                    chosenOrder = Order()
                    b_delete.isVisible = false
                } else {
                    chosenLayout!!.setBackgroundColor(resources.getColor(R.color.colorWhite))
                    chosenLayout = p0.itemView.findViewById<LinearLayout>(R.id.ll_background)
                    chosenOrder = p2
                    b_delete.isVisible = true
                }
            }

        }

        /**
         * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
         * an item.
         *
         *
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         *
         *
         * The new ViewHolder will be used to display items of the adapter using
         * [.onBindViewHolder]. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary [View.findViewById] calls.
         *
         * @param parent The ViewGroup into which the new View will be added after it is bound to
         * an adapter position.
         * @param viewType The view type of the new View.
         *
         * @return A new ViewHolder that holds a View of the given view type.
         * @see .getItemViewType
         * @see .onBindViewHolder
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
            return OrderViewHolder(view) }
    }


}
