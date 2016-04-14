package ca.bcit.comp3717.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kevin on 2/6/2016.
 */
public class TaskListViewAdapter extends ArrayAdapter<TaskListViewItem>{

    List<TaskListViewItem> list;

    public TaskListViewAdapter(Context context, List<TaskListViewItem> items) {
        super(context, R.layout.task_list_item, items);
        this.list = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Button deleteBtn = (Button)convertView.findViewById(R.id.delete_btn);

        // update the item view
        TaskListViewItem item = getItem(position);
        viewHolder.ivIcon.setImageDrawable(item.icon);
        viewHolder.tvTitle.setText(item.title);
        viewHolder.tvDescription.setText(item.description);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something

                DataDbHelper db = new DataDbHelper(v.getContext());
                db.delete(list.get(position).id);
                Toast.makeText(v.getContext(),"Deleting " + position, Toast.LENGTH_LONG).show();
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     */
    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvDescription;
    }


}
