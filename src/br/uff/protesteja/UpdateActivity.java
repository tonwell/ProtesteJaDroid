package br.uff.protesteja;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.uff.utils.HTTPUtils;

public class UpdateActivity extends Activity {
	private EditText updNome, updProtesto;
	private Button btnUpdate;
	private Map<String, String> entrada;
	private JSONObject obj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		updNome = (EditText) findViewById(R.id.edt_nome_update);
		updProtesto = (EditText) findViewById(R.id.edt_protesto_update);
		btnUpdate = (Button) findViewById(R.id.btn_atualizar_update);
		try {
			obj = new JSONObject(getIntent().getStringExtra("itemId"));
			updNome.setText(obj.getString("pessoa"));
			updProtesto.setText(obj.getString("descricao"));
		} catch (Exception e) {
			Log.e("Erro", "Json");
		}

		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (updNome.getText().toString().equals("")
						|| updNome.getText().toString()
								.equalsIgnoreCase("anonimo")
						|| updProtesto.getText().toString().equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"Todos os campos devem ser preenchidos e o campo nome não pode ser \"anonimo\"!",
							Toast.LENGTH_LONG).show();
				} else {
					entrada = new HashMap<String, String>();
					entrada.put("pessoa", updNome.getText().toString());
					entrada.put("descricao", updProtesto.getText().toString());
					new Upar().execute();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.update, menu);
		return true;
	}

	private class Upar extends AsyncTask<Void, Void, Boolean> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(UpdateActivity.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			dialog.dismiss();
			Toast.makeText(getApplicationContext(),
					(result) ? "Sucesso" : "Falha ao enviar", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				return HTTPUtils.atualizar(
						obj.getString("url"), entrada);
			} catch (JSONException e) {
				Log.e("Erro","Json");
				return false;
			}
		}
	}

}
