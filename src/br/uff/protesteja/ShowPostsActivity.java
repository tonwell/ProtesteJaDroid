package br.uff.protesteja;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.uff.utils.HTTPUtils;

public class ShowPostsActivity extends Activity {

	private ListView lstProtestos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_posts);

		lstProtestos = (ListView) findViewById(R.id.lst_protestos);
		new Getar().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_posts, menu);
		return true;
	}

	public void atualizar(View v) {
		new Getar().execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		new Getar().execute();
	}

	private class Getar extends AsyncTask<Void, Void, String[]> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ShowPostsActivity.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(String[] result) {
			dialog.dismiss();

			if (result != null) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getBaseContext(), android.R.layout.simple_list_item_1,
						result);
				lstProtestos.setAdapter(adapter);
				if (adapter.isEmpty())
					Toast.makeText(getApplicationContext(), "Lista vazia",
							Toast.LENGTH_LONG).show();
			} else
				Toast.makeText(getApplicationContext(), "Erro na conexão",
						Toast.LENGTH_LONG).show();

		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				String url = "https://droid-list.herokuapp.com/protestos.json";
				String conteudo = HTTPUtils.acessar(url);
				JSONArray resultados = new JSONArray(conteudo);

				String[] protestos = new String[resultados.length()];

				for (int i = 0; i < resultados.length(); i++) {
					JSONObject protesto = resultados.getJSONObject(i);
					String pessoa = protesto.getString("pessoa");
					String descricao = protesto.getString("descricao");
					protestos[i] = pessoa + " - " + descricao;
				}
				return protestos;
			} catch (Exception e) {
				return null;
			}
		}

	}

}
