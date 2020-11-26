package com.example.androidprojectbooksales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidprojectbooksales.books.Book;
import com.example.androidprojectbooksales.books.ViewBook_Fragment;
import com.squareup.picasso.Picasso;

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
        holder.tvBookId.setText(p.getId());
        holder.tvBookTitle.setText(p.getTitle());
        Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/bookPictures/"
                + p.getId() + ".png").resize(110, 150).into(holder.imgBookCover);
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
        TextView tvBookId, tvBookTitle;
        ImageView imgBookCover;
        final Context context = itemView.getContext();
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookId = itemView.findViewById(R.id.tvBookId);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            imgBookCover = itemView.findViewById(R.id.imgBookCover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("com.example.androidprojectbooksales.VIEW_BOOK");
                    intent.putExtra("id",bookList.get(getLayoutPosition()).getId());
                    intent.putExtra("owner",bookList.get(getLayoutPosition()).getOwner());
                    context.sendBroadcast(intent);
                }
            });
        }
    }
}
