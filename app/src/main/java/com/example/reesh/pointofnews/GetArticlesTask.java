package com.example.reesh.pointofnews;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class GetArticlesTask extends AsyncTask<ArrayList,Void,ArrayList> {
    public ArrayList<Article> getArticles() {
        return articles;
    }

    private ListView listView;
    private ArrayList<Article> articles;

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    protected ArrayList doInBackground(ArrayList... arrayLists) {
        ArrayList<String> query=arrayLists[0];
        articles=Utils.getArticles(query);
        return articles;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        for (Article article:articles){
            switch (article.getSentimentVerdict()){

                case "positive":
                    article.setVerdictLogoResourceId(R.drawable.baseline_sentiment_very_satisfied_24);
                    break;

                case "neutral":
                    article.setVerdictLogoResourceId(R.drawable.baseline_sentiment_satisfied_24);
                    break;

                case "negative":
                    article.setVerdictLogoResourceId(R.drawable.baseline_sentiment_very_dissatisfied_24);
                    break;
            }
        }

        ArticleAdapter articleAdapter=new ArticleAdapter(MainActivity.getContext(),R.layout.news_item,articles);

        listView.setAdapter(articleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=articles.get(position).getUrl();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                MainActivity.getContext().startActivity(intent);
            }
        });
    }
}
