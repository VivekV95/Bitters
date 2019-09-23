package com.vivekvishwanath.bitters.views;


import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.Utils.CocktailUtils;
import com.vivekvishwanath.bitters.adapters.IngredientListAdapter;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.models.Ingredients;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class CustomCocktailFragment extends Fragment {

    private EditText customCocktailName;
    private CheckBox showIngredientsBox;
    private EditText instructionsText;
    private Button saveButton;
    private ImageView cocktailImage;
    private FloatingActionButton addImageButton;
    private EditText searchIngredientText;

    MediaPlayer mediaPlayer;

    private RecyclerView allIngredientsRecyclerView;
    private RecyclerView.LayoutManager allIngredientsLayoutManager;
    private IngredientListAdapter allIngredientsListAdapter;

    private RecyclerView selectedIngredientsRecyclerView;
    private RecyclerView.LayoutManager selectedIngredientsLayoutManager;
    public static IngredientListAdapter selectedIngredientsListAdapter;

    private CocktailViewModel viewModel;
    private Context context;
    public static ArrayList<Ingredient> selectedIngredients;

    public static final int IMAGE_REQUEST_CODE = 3;
    private int cocktail_id;

    public CustomCocktailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_cocktail, container, false);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedIngredients = new ArrayList<>();
        context = getActivity();
        viewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);
        if (viewModel.getSelectedIngredients().getValue() == null) {
            viewModel.getSelectedIngredients().setValue(selectedIngredients);
        }

        showIngredientsBox = view.findViewById(R.id.show_ingredients_button);
        showIngredientsBox.setOnCheckedChangeListener(showBoxListener);

        customCocktailName = view.findViewById(R.id.custom_cocktail_name);

        addImageButton = view.findViewById(R.id.add_image_button);
        addImageButton.setOnClickListener(imageListener);

        searchIngredientText = view.findViewById(R.id.ingredient_search);
        searchIngredientText.addTextChangedListener(searchIngredientTextWatcher);

        cocktailImage = view.findViewById(R.id.custom_cocktail_image);
        cocktailImage.setOnClickListener(imageListener);
        if (viewModel.getCocktailImage().getValue() != null) {
            cocktailImage.setImageBitmap(viewModel.getCocktailImage().getValue());
            addImageButton.setVisibility(View.INVISIBLE);
        }

        instructionsText = view.findViewById(R.id.custom_cocktail_instructions_text);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(saveButtonListener);

        allIngredientsRecyclerView = getView().findViewById(R.id.custom_cocktail_all_ingredients);
        allIngredientsRecyclerView.setHasFixedSize(true);
        allIngredientsLayoutManager = new GridLayoutManager(getContext(), 3);
        allIngredientsRecyclerView.setLayoutManager(allIngredientsLayoutManager);

        selectedIngredientsRecyclerView = view.findViewById(R.id.custom_cocktail_selected_ingredients);
        selectedIngredientsRecyclerView.setHasFixedSize(true);
        selectedIngredientsLayoutManager = new GridLayoutManager(getContext(), 3);
        selectedIngredientsRecyclerView.setLayoutManager(selectedIngredientsLayoutManager);

        viewModel.getAllIngredients().observe(getActivity(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Ingredient> ingredients) {
                allIngredientsListAdapter = new IngredientListAdapter(ingredients, context, viewModel, true, false);
                allIngredientsRecyclerView.setAdapter(allIngredientsListAdapter);
            }
        });

        viewModel.getSelectedIngredients().observe(getActivity(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Ingredient> ingredients) {
                selectedIngredientsListAdapter = new IngredientListAdapter(ingredients, context, viewModel, true, true);
                selectedIngredientsRecyclerView.setAdapter(selectedIngredientsListAdapter);
            }
        });

        mediaPlayer = MediaPlayer.create(context, R.raw.shaker_sound);
    }

    CompoundButton.OnCheckedChangeListener showBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                allIngredientsRecyclerView.setVisibility(View.VISIBLE);
            } else {
                allIngredientsRecyclerView.setVisibility(View.GONE);
            }
        }
    };

    View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkFields()) {
                cocktail_id = createId();
                final Cocktail cocktail = new Cocktail(Integer.toString(cocktail_id));
                cocktail.setDrinkName(customCocktailName.getText().toString());
                Ingredients ingredients = CocktailUtils.createIngredientsObject(viewModel.getSelectedIngredients().getValue());
                cocktail.setIngredients(ingredients);
                cocktail.getIngredients().setIngredientsId(Integer.parseInt(cocktail.getDrinkId()));
                cocktail.setInstructions(instructionsText.getText().toString());
                storeImage(viewModel.getCocktailImage().getValue(), cocktail_id, new UriPathCallback() {
                    @Override
                    public void onUriPathReceived(String path) {
                        cocktail.setPhotoUrl(path);
                    }
                });
                viewModel.addCustomCocktail(cocktail);
                Snackbar.make(getView(), "Cocktail Created!", Snackbar.LENGTH_LONG).show();
                mediaPlayer.start();
            } else {
                Toast.makeText(context, "Make sure your cocktail has a name, between 2 and 15 ingredients" +
                        ", an image, and instructions", Toast.LENGTH_LONG).show();
            }
        }
    };

    TextWatcher searchIngredientTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String searchText = s.toString().toLowerCase();
            ArrayList<Ingredient> matchedIngredients = new ArrayList<>();
            if (viewModel.getAllIngredients().getValue() != null) {
                for (int i = 0; i < viewModel.getAllIngredients().getValue().size(); i++) {
                    if (viewModel.getAllIngredients().getValue().get(i).getName().toLowerCase().contains(searchText)) {
                        matchedIngredients.add(viewModel.getAllIngredients().getValue().get(i));
                    }
                }
                allIngredientsListAdapter = new IngredientListAdapter(matchedIngredients, context, viewModel, true, false);
                allIngredientsRecyclerView.setAdapter(allIngredientsListAdapter);
            }
        }
    };

    View.OnClickListener imageListener = new View.OnClickListener() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onClick(View v) {
            if (viewModel.getCocktailImage().getValue() == null) {
                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                cocktail_id = createId();
                imageIntent.putExtra("id", cocktail_id);
                imageIntent.setType("image/*");
                getActivity().setResult(Activity.RESULT_OK, imageIntent);
                getActivity().startActivityForResult(imageIntent, IMAGE_REQUEST_CODE);
            } else {
                viewModel.getCocktailImage().setValue(null);
                cocktailImage.setImageDrawable(null);
                addImageButton.setVisibility(View.VISIBLE);
            }
        }
    };


    private boolean checkFields() {
        if (TextUtils.isEmpty(customCocktailName.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(instructionsText.getText().toString())) {
            return false;
        } else if (viewModel.getSelectedIngredients().getValue().size() < 2
                || viewModel.getSelectedIngredients().getValue().size() > 15) {
            return false;
        } else if (cocktailImage.getDrawable() == null) {
            return false;
        }
        return true;
    }

    private int createId() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        if (viewModel.getCustomIds().getValue().contains(num)) {
            createId();
        }
        return num;
    }

    private void storeImage(final Bitmap bitmap,
                              final int cocktailId, final UriPathCallback uriPathCallback) {
        String uriPath = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap image = bitmap;
                File directory = new File(context.getFilesDir(), "imageDir");
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File myPath = new File(directory, cocktailId + ".png");
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(myPath);
                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    uriPathCallback.onUriPathReceived(Uri.parse(myPath.getAbsolutePath()).toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    interface UriPathCallback {
        void onUriPathReceived(String path);
    }


}
