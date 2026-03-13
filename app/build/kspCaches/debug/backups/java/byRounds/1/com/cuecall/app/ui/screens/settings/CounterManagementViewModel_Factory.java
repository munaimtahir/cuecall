package com.cuecall.app.ui.screens.settings;

import com.cuecall.app.domain.repository.CounterRepository;
import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.usecase.SetupValidator;
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

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SetupValidator> setupValidatorProvider;

  public CounterManagementViewModel_Factory(Provider<CounterRepository> counterRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    this.counterRepositoryProvider = counterRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.setupValidatorProvider = setupValidatorProvider;
  }

  @Override
  public CounterManagementViewModel get() {
    return newInstance(counterRepositoryProvider.get(), serviceRepositoryProvider.get(), setupValidatorProvider.get());
  }

  public static CounterManagementViewModel_Factory create(
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    return new CounterManagementViewModel_Factory(counterRepositoryProvider, serviceRepositoryProvider, setupValidatorProvider);
  }

  public static CounterManagementViewModel newInstance(CounterRepository counterRepository,
      ServiceRepository serviceRepository, SetupValidator setupValidator) {
    return new CounterManagementViewModel(counterRepository, serviceRepository, setupValidator);
  }
}
