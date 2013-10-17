package br.uff.protesteja;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.uff.utils.HTTPUtils;

public class ShowPostsActivity extends Activity {

	private ListView lstProtestos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_posts);

		lstProtestos = (ListView) findViewById(R.id.lst_protestos);
		new Postar().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_posts, menu);
		return true;
	}
	
	public void atualizar(View v){
		new Postar().execute();
	}
	
	private class Postar extends AsyncTask<Void, Void, String[]> {
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ShowPostsActivity.this);
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String[] result) {


			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getBaseContext(), android.R.layout.simple_list_item_1,
					result);
			lstProtestos.setAdapter(adapter);
			dialog.dismiss();
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				String urlD = "https://droid-list.herokuapp.com/pessoas.json";
				String url = Uri.parse(urlD).toString();
				String conteudo = HTTPUtils.acessar(url);
				JSONArray resultados = new JSONArray(conteudo);

				String[] pessoas = new String[resultados.length()];

				for (int i = 0; i < resultados.length(); i++) {
					JSONObject pessoa = resultados.getJSONObject(i);
					String nome = pessoa.getString("nome");
					String tel = pessoa.getString("tel");
					pessoas[i] = nome + " - " + tel;
				}
				return pessoas;
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		
	}

}
