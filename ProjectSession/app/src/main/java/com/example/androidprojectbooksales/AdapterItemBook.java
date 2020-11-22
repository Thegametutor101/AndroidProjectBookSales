package com.example.androidprojectbooksales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidprojectbooksales.books.Book;

import java.util.List;

public class AdapterItemBook extends RecyclerView.Adapter<AdapterItemBook.BookViewHolder> {

    List<Book> bookList;

    public AdapterItemBook(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_item_book_layout, parent, false);
        return  new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
//        Product p = productList.get(position);
//        holder.tvName.setText(p.getName());
//        holder.tvPrice.setText(p.getPrice() + " $");
//        if (p.isAvailable()) {
//            holder.tvAvailable.setText("En Stock");
//        } else {
//            holder.tvAvailable.setText("Rupture de Stock");
//        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void add(Book book) {
        bookList.add(book);
        notifyItemInserted(bookList.size() - 1);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
//        TextView tvName, tvPrice, tvAvailable;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvName = itemView.findViewById(R.id.tvName);
//            tvPrice = itemView.findViewById(R.id.tvPrice);
//            tvAvailable = itemView.findViewById(R.id.tvAvailable);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String name = productList.get(getLayoutPosition()).getName();
//                    Toast.makeText(itemView.getContext(), name, Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }
}
