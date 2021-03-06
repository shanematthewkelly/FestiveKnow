// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.shanekelly.example.festiveknow.viewmodel;

import com.shanekelly.example.festiveknow.data.ListItemRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CustomViewModelFactory_Factory implements Factory<CustomViewModelFactory> {
  private final Provider<ListItemRepository> repositoryProvider;

  public CustomViewModelFactory_Factory(Provider<ListItemRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CustomViewModelFactory get() {
    return new CustomViewModelFactory(repositoryProvider.get());
  }

  public static Factory<CustomViewModelFactory> create(
      Provider<ListItemRepository> repositoryProvider) {
    return new CustomViewModelFactory_Factory(repositoryProvider);
  }
}
