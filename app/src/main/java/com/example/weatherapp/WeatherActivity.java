    package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

    public class WeatherActivity extends AppCompatActivity {

    private TextView city,temperature, weatherCondition,humidity,maxTemp,minTemp,pressure,wind;
    private ImageView imageView;
    private Button search;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        city = findViewById(R.id.textViewCityWeather);
        temperature = findViewById(R.id.textViewTempWeather);
        weatherCondition = findViewById(R.id.textViewWeatherConditionWeather);
        humidity = findViewById(R.id.textViewHumidityWeather);
        maxTemp = findViewById(R.id.textViewMaxTempWeather);
        minTemp = findViewById(R.id.textViewMinTempWeather);
        pressure = findViewById(R.id.textViewPressureWeather);
        wind = findViewById(R.id.textViewWindWeather);
        imageView = findViewById(R.id.imageViewWeather);
        search = findViewById(R.id.search);
        editText = findViewById(R.id.editTextCityName);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cityName = editText.getText().toString();

                getWeatherData(cityName);
                editText.setText("");
            }
        });
    }
        public void getWeatherData(String name){
            WeatherApi weatherApi = RetrofitWeather.getCLient().create(WeatherApi.class);
            Call<OpenWeatherMap> call = weatherApi.getWeatherWithCity(name);
            call.enqueue(new Callback<OpenWeatherMap>() {
                @Override
                public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {

                    if (response.isSuccessful()) {

                        city.setText(response.body().getName() + " , " + response.body().getSys().getCountry());
                        temperature.setText(response.body().getMain().getTemp() + "°C");
                        weatherCondition.setText(response.body().getWeather().get(0).getDescription());
                        humidity.setText(" : " + response.body().getMain().getHumidity() + "%");
                        maxTemp.setText(" : " + response.body().getMain().getTempMax() + "°C");
                        minTemp.setText(" : " + response.body().getMain().getTempMin() + "°C");
                        pressure.setText(" : " + response.body().getMain().getPressure());
                        wind.setText(" : " + response.body().getWind().getSpeed());

                        String iconCode = response.body().getWeather().get(0).getIcon();
                        Picasso.get().load("https://openweathermap.org/img/wn/" + iconCode + "@2x.png").placeholder(R.drawable.ic_launcher_background).into(imageView);
                    }else {
                        Toast.makeText(WeatherActivity.this, "city name invalid", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OpenWeatherMap> call, Throwable t) {

                }
            });
        }
}