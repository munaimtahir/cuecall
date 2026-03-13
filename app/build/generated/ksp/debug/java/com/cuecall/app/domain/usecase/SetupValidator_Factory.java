package com.cuecall.app.domain.usecase;

import com.cuecall.app.domain.repository.ClinicRepository;
import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SetupValidator_Factory implements Factory<SetupValidator> {
  private final Provider<ClinicRepository> clinicRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<QueueDayRepository> queueDayRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public SetupValidator_Factory(Provider<ClinicRepository> clinicRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.clinicRepositoryProvider = clinicRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public SetupValidator get() {
    return newInstance(clinicRepositoryProvider.get(), serviceRepositoryProvider.get(), queueDayRepositoryProvider.get(), settingsRepositoryProvider.get());
  }

  public static SetupValidator_Factory create(Provider<ClinicRepository> clinicRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new SetupValidator_Factory(clinicRepositoryProvider, serviceRepositoryProvider, queueDayRepositoryProvider, settingsRepositoryProvider);
  }

  public static SetupValidator newInstance(ClinicRepository clinicRepository,
      ServiceRepository serviceRepository, QueueDayRepository queueDayRepository,
      SettingsRepository settingsRepository) {
    return new SetupValidator(clinicRepository, serviceRepository, queueDayRepository, settingsRepository);
  }
}
