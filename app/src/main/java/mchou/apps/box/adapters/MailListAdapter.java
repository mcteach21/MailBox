package mchou.apps.box.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import mchou.apps.box.R;

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
        holder.from.setText(item.getFrom());
        holder.subject.setText(item.getSubject());
        holder.body.setText(item.getBody()); //.length()<50?item.getBody():item.getBody().subSequence(0,50)+"..");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm");
            holder.date.setText(item.getDate().format(formatter));
        }

        holder.img.setImageResource(R.drawable.mail_logo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, from, body, date;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.subject);
            from = (TextView) itemView.findViewById(R.id.from);
            body = (TextView) itemView.findViewById(R.id.body);
            date = (TextView) itemView.findViewById(R.id.date);

            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
