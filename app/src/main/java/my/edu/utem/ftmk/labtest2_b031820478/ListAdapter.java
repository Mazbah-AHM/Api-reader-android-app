package my.edu.utem.ftmk.labtest2_b031820478;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<University> universities;
    LayoutInflater inflater;

    public ListAdapter(Context context,ArrayList<University> universities){
        this.universities = universities;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.row, null);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((SimpleViewHolder) holder).bindData(universities.get(position));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }





    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        private TextView simpleTextView;

        public SimpleViewHolder(final View itemView) {
            super(itemView);
            simpleTextView = (TextView) itemView.findViewById(R.id.name);

        }
        public void bindData(final University viewModel) {
            simpleTextView.setText(viewModel.name);
            simpleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAllData(viewModel);
                }
            });
        }
    }

    public void showAllData(University university){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_view, null);
        TextView nameView = view.findViewById(R.id.uni_name);
        ListView listView = view.findViewById(R.id.listview);
        nameView.setText(university.name);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, university.websites);
        listView.setAdapter(adapter);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
