package mchou.apps.box.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import mchou.apps.box.R;
import mchou.apps.box.tools.Utils;

public class MailListAdapter extends RecyclerView.Adapter<MailListAdapter.ViewHolder> {
    List<Mail> items;
    public MailListAdapter(List<Mail> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mail_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mail item = items.get(position);
        holder.from.setText(item.getSender()); //item.getFrom()
        holder.subject.setText(item.getSubject());
        holder.body.setText(item.getBody());
        holder.date.setText(item.getDate());

        holder.img.setImageResource(R.drawable.mail_logo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Mail> items) {
        this.items = items;
    }

    List<Integer> opened = new ArrayList();
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, from, body, date;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.subject);
            from =  itemView.findViewById(R.id.from);
            body =  itemView.findViewById(R.id.body);
            date =  itemView.findViewById(R.id.date);

            img =  itemView.findViewById(R.id.img);

            Log.i("tests", "ViewHolder: "+body.getLayoutParams().height);

            itemView.setOnClickListener(e->{
                    if(opened.contains(getAdapterPosition())) {
                        Utils.collapse(body);
                        opened.remove((Object)getAdapterPosition());
                    }else{
                        Utils.expand(body);
                        opened.add(getAdapterPosition());
                    }
                });
        }
    }
}
