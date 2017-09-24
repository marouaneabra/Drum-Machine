package oberlin.drummachinehackv2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    //private String[] mData = new String[0];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<Row> rows;
    private Context context;
    private Realm realm;


    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<Row> rows) {
        this.mInflater = LayoutInflater.from(context);
        this.rows = rows;
        this.context = context;

//        RealmResults<Row> realmRows =
//                realm.where(Row.class).findAll();
//
//
//        rows = new ArrayList<Row>();
//
//        for (int i = 0; i < realmRows.size(); i++) {
//            rows.add(realmRows.get(i));
//        }


        //this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String animal = mData[position];
        Button[] buttons = holder.buttons;
        Row row = rows.get(position);
        for (int i = 0; i < buttons.length; i++) {
            if(buttons[i].isSelected()){
                row.setButtonSelected(i, true);
            }else{
                row.setButtonSelected(i, false);
            }
        }

    }

    private void moveMetronome() {
        for (int i = 0; i < rows.size(); i++) {
            rows.get(i).moveMetronome();
        }
        this.notifyDataSetChanged();
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return rows.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn1;
        Button btn2;
        Button btn3;
        Button btn4;
        Button btn5;
        Button btn6;
        Button btn7;
        Button btn8;
        Button[] buttons;

        ViewHolder(View itemView) {
            super(itemView);
            buttons = new Button[8];
            btn1 = (Button) itemView.findViewById(R.id.btn1);
            btn2 = (Button) itemView.findViewById(R.id.btn2);
            btn3 = (Button) itemView.findViewById(R.id.btn3);
            btn4 = (Button) itemView.findViewById(R.id.btn4);
            btn5 = (Button) itemView.findViewById(R.id.btn5);
            btn6 = (Button) itemView.findViewById(R.id.btn6);
            btn7 = (Button) itemView.findViewById(R.id.btn7);
            btn8 = (Button) itemView.findViewById(R.id.btn8);




            buttons[0] = btn1;
            buttons[1] = btn2;
            buttons[2] = btn3;
            buttons[3] = btn4;
            buttons[4] = btn5;
            buttons[5] = btn6;
            buttons[6] = btn7;
            buttons[7] = btn8;

            for (int i = 0; i < buttons.length; i++) {
                setBtnClickListener(buttons[i]);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    private void setBtnClickListener(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMetronome();

                if(!v.isSelected()){
                    v.setSelected(true);
                    v.setBackgroundColor(Color.YELLOW);
                }else{
                    v.setSelected(false);
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGray));
                }
            }
        });
    }

    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData[id];
//    }




    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}