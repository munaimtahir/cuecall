package com.cuecall.app.ui.screens.settings;

import com.cuecall.app.domain.repository.ClinicRepository;
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
public final class ClinicSetupViewModel_Factory implements Factory<ClinicSetupViewModel> {
  private final Provider<ClinicRepository> clinicRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<CounterRepository> counterRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<SetupValidator> setupValidatorProvider;

  public ClinicSetupViewModel_Factory(Provider<ClinicRepository> clinicRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    this.clinicRepositoryProvider = clinicRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.counterRepositoryProvider = counterRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.setupValidatorProvider = setupValidatorProvider;
  }

  @Override
  public ClinicSetupViewModel get() {
    return newInstance(clinicRepositoryProvider.get(), serviceRepositoryProvider.get(), counterRepositoryProvider.get(), settingsRepositoryProvider.get(), setupValidatorProvider.get());
  }

  public static ClinicSetupViewModel_Factory create(
      Provider<ClinicRepository> clinicRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    return new ClinicSetupViewModel_Factory(clinicRepositoryProvider, serviceRepositoryProvider, counterRepositoryProvider, settingsRepositoryProvider, setupValidatorProvider);
  }

  public static ClinicSetupViewModel newInstance(ClinicRepository clinicRepository,
      ServiceRepository serviceRepository, CounterRepository counterRepository,
      SettingsRepository settingsRepository, SetupValidator setupValidator) {
    return new ClinicSetupViewModel(clinicRepository, serviceRepository, counterRepository, settingsRepository, setupValidator);
  }
}
