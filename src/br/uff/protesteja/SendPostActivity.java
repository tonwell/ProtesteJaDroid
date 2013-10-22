package br.uff.protesteja;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.uff.utils.HTTPUtils;

public class SendPostActivity extends Activity {

	private EditText edtNome;
	private EditText edtProtesto;
	private Map<String, String> entrada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_post);

		edtNome = (EditText) findViewById(R.id.edt_nome);
		edtProtesto = (EditText) findViewById(R.id.edt_protesto);
	}

	public void protestar(View v) {
		if (edtNome.getText().toString().equals("")
				|| edtNome.getText().toString().equalsIgnoreCase("anonimo")
				|| edtProtesto.getText().toString().equals("")) {
			Toast.makeText(
					getApplicationContext(),
					"Todos os campos devem ser preenchidos e o campo nome não pode ser \"anonimo\"!",
					Toast.LENGTH_LONG).show();
		} else {
			entrada = new HashMap<String, String>();
			entrada.put("pessoa", edtNome.getText().toString());
			entrada.put("descricao", edtProtesto.getText().toString());
			new Postar().execute();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.send_post, menu);
		return true;
	}

	private class Postar extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SendPostActivity.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			Toast.makeText(getApplicationContext(),
					(result != null) ? "Sucesso" : "Falha ao enviar",
					Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(Void... params) {
			return HTTPUtils.postar("https://droid-list.herokuapp.com/protestos",
					entrada);
		}
	}

}
