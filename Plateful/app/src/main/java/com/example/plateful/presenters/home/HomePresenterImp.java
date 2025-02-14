package com.example.plateful.presenters.home;


import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.views.home.HomeView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomePresenterImp implements HomePresenter {
    private HomeView homeView;
    private DataRepository dataRepository;

    public HomePresenterImp(HomeView homeView) {
        this.homeView = homeView;
        dataRepository = DataRepository.getInstance();
    }

    @Override
    public void getDailyMeal() {
        dataRepository.getDailyMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.showDailyMeal(success.getMeals().get(0));
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getCategories() {
        dataRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.showCategories(success.getCategories());
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getCountry() {
        dataRepository.getCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.showCountries(success.getMeals());
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

}
