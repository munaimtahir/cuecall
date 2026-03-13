package com.cuecall.app.ui.screens.settings;

import com.cuecall.app.domain.repository.CounterRepository;
import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
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
public final class DeviceAssignmentViewModel_Factory implements Factory<DeviceAssignmentViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<CounterRepository> counterRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SetupValidator> setupValidatorProvider;

  public DeviceAssignmentViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.counterRepositoryProvider = counterRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.setupValidatorProvider = setupValidatorProvider;
  }

  @Override
  public DeviceAssignmentViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), counterRepositoryProvider.get(), serviceRepositoryProvider.get(), setupValidatorProvider.get());
  }

  public static DeviceAssignmentViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    return new DeviceAssignmentViewModel_Factory(settingsRepositoryProvider, counterRepositoryProvider, serviceRepositoryProvider, setupValidatorProvider);
  }

  public static DeviceAssignmentViewModel newInstance(SettingsRepository settingsRepository,
      CounterRepository counterRepository, ServiceRepository serviceRepository,
      SetupValidator setupValidator) {
    return new DeviceAssignmentViewModel(settingsRepository, counterRepository, serviceRepository, setupValidator);
  }
}
