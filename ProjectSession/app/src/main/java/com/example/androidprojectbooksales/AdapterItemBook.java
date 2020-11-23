package com.example.androidprojectbooksales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        Book p = bookList.get(position);
        holder.tvBookName.setText(p.getTitle());
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
        TextView tvBookName;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tvBookName);
        }
    }
}
