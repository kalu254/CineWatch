package com.example.cinewatch.ui.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cinewatch.adapters.WishListAdapter;
import com.example.cinewatch.databinding.WishlistMoviesBinding;
import com.example.cinewatch.db.WishListMovie;
import com.example.cinewatch.viewmodel.WishListViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Created by Abhinav Singh on 12,June,2020
 */
@AndroidEntryPoint
public class WishListFragment extends Fragment {
    private static final String TAG = "WishListFragment";
    
    private WishListViewModel viewModel;
    private WishlistMoviesBinding binding;
    private WishListAdapter adapter;
    private List<WishListMovie> moviesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WishlistMoviesBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return (view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(WishListFragment.this).get(WishListViewModel.class);
        
        intiRecyclerView();
        observeData();

        binding.clearWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.clearWishList();
                if (moviesList != null){
                    moviesList.clear();
                    Toast.makeText(getContext(),"WishList cleared!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(),"Your Wishlist is empty!",Toast.LENGTH_SHORT).show();
                }
                adapter.setMoviesList(moviesList);

            }
        });
        
    }

    private void observeData() {
        viewModel.getWishListMoviesList().observe(getViewLifecycleOwner(), new Observer<List<WishListMovie>>() {
            @Override
            public void onChanged(List<WishListMovie> wishListMovies) {
                if (wishListMovies.size() == 0 || wishListMovies == null){
                    binding.placeHolderText.setVisibility(View.VISIBLE);
                    binding.noItemsPlaceHolder.setVisibility(View.VISIBLE);
                }
                else{
                    binding.placeHolderText.setVisibility(View.GONE);
                    binding.noItemsPlaceHolder.setVisibility(View.GONE);
                    adapter.setMoviesList(wishListMovies);
                    moviesList = wishListMovies;
                }

            }
        });

    }

    private void intiRecyclerView() {
        binding.wishListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter  = new WishListAdapter(getContext(),moviesList);
        binding.wishListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
