package com.cuecall.app.ui.screens.settings;

import com.cuecall.app.domain.repository.CounterRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class CounterManagementViewModel_Factory implements Factory<CounterManagementViewModel> {
  private final Provider<CounterRepository> counterRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public CounterManagementViewModel_Factory(Provider<CounterRepository> counterRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.counterRepositoryProvider = counterRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public CounterManagementViewModel get() {
    return newInstance(counterRepositoryProvider.get(), settingsRepositoryProvider.get());
  }

  public static CounterManagementViewModel_Factory create(
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new CounterManagementViewModel_Factory(counterRepositoryProvider, settingsRepositoryProvider);
  }

  public static CounterManagementViewModel newInstance(CounterRepository counterRepository,
      SettingsRepository settingsRepository) {
    return new CounterManagementViewModel(counterRepository, settingsRepository);
  }
}
