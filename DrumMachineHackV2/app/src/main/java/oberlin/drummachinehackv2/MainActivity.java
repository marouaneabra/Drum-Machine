package oberlin.drummachinehackv2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    ArrayList<Row> rows;
    Button testBtn;
    int numButtonsPerRow;
    ViewGroup rowLayout;
    Realm realmObj;
    Metronome metronome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //realmObj = ((MainApplication)getApplication()).getRealmTodo();
        metronome = new Metronome(this, 120, 8);

        //setUpToolBar();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        numButtonsPerRow = 8;
        rowLayout = (ViewGroup) findViewById(R.id.rowLayout);
        setUpottomNavBar();

        //((MainApplication)getApplication()).openRealm();


//        for (int i = 0; i < numButtonsPerRow; i++) {
//            View child = getLayoutInflater().inflate(R.layout.pad, null);
//
//            rowLayout.addView(child);
////            Button btn = new Button(this);
////            //btn.setId(i);
////            rowLayout.addView(btn);
//        }


        rows = new ArrayList<Row>();
        makeRows(4);

        // data to populate the RecyclerView with
        String[] data = {"1", "2"};

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));;
        adapter = new MyRecyclerViewAdapter(this, rows);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

//    realmTodo.beginTransaction();
//    Todo newTodo = realmTodo.createObject(Todo.class, UUID.randomUUID().toString());
//        newTodo.setTodoText(name);
//        newTodo.setTodoDescription(description);
//        newTodo.setTodoCategory(category);
//        newTodo.setTodoPrice(price);
//        newTodo.setDone(false);
//
//
//        realmTodo.commitTransaction();
//
//        todoList.add(0, newTodo);
//    notifyItemInserted(0);

    private void setUpottomNavBar() {
        BottomNavigationView bottomToolbar = (BottomNavigationView) findViewById(R.id.bottomToolbar);
        bottomToolbar.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.loadBtn:

                                break;
                            case R.id.saveBtn:
//                                realmObj.beginTransaction();
//                                    Row newRow = realmObj.createObject(Todo.class, UUID.randomUUID().toString());
//                                        newTodo.setTodoText(name);
//                                        newTodo.setTodoDescription(description);
//                                        newTodo.setTodoCategory(category);
//                                        newTodo.setTodoPrice(price);
//                                        newTodo.setDone(false);
//
//
//                                        realmTodo.commitTransaction();
//
//                                        todoList.add(0, newTodo);
//                                    notifyItemInserted(0);
                                break;
                            case R.id.playBtn:
                                System.out.print("\n\nplaying loop!!\n\n");
                                boolean[][] selectedButtons = getSelectedButtons();
                                int[][] formattedSelectedButtons = getFormattedSelectedButtons(selectedButtons);
                                metronome.doTheThing(formattedSelectedButtons);

//                                for (int i = 0; i < rows.size(); i++) {
//                                    for (int j = 0; j < numButtonsPerRow; j++) {
//                                        System.out.print(formattedSelectedButtons[i][j] + ", ");
//                                    }
//                                    System.out.println();
//                    //            }
//                                break;
                        }

                        return true;
                    }
                });

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        System.out.println("\n\nclicked play button\n\n");
//
//
//        if (id == R.id.action_playBtn) {
//            boolean[][] selectedButtons = getSelectedButtons();
//            int[][] formattedSelectedButtons = getFormattedSelectedButtons(selectedButtons);
//            for (int i = 0; i < rows.size(); i++) {
//                for (int j = 0; j < numButtonsPerRow; j++) {
//                    System.out.print(formattedSelectedButtons[i][j] + ", ");
//                }
//                System.out.println();
//            }
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void moveMetronome() {
        for (int i = 0; i < rows.size(); i++) {
            rows.get(i).moveMetronome();
        }
        adapter.notifyDataSetChanged();
    }

    public void makeRows(int numRows){
        for (int i = 0; i < numRows; i++) {
            rows.add(new Row(numButtonsPerRow, this));
        }
    }

    public int[][] getFormattedSelectedButtons(boolean[][] selectedButtons){
        int[][] formattedSelectedButtons = new int[rows.size()][numButtonsPerRow];
        for (int i = 0; i < numButtonsPerRow; i++) {
            for (int j = 0; j < rows.size(); j++) {
                if(selectedButtons[j][i] == true){
                    formattedSelectedButtons[j][i] = j + 1;
                }else{
                    formattedSelectedButtons[j][i] = 0;
                }
            }
        }

        return formattedSelectedButtons;
    }

    public boolean[][] getSelectedButtons(){
        boolean[][] selectedButtons = new boolean[rows.size()][numButtonsPerRow];
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < numButtonsPerRow; j++) {
                selectedButtons[i][j] = rows.get(i).isSelected(j);
            }
        }


        return selectedButtons;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //((MainApplication)getApplication()).closeRealm();
    }


    @Override
    public void onItemClick(View view, int position) {
        //Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}