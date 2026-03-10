package com.cuecall.app.domain.usecase;

import com.cuecall.app.domain.repository.ServiceRepository;
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
public final class SetupClinicUseCase_Factory implements Factory<SetupClinicUseCase> {
  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public SetupClinicUseCase_Factory(Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public SetupClinicUseCase get() {
    return newInstance(serviceRepositoryProvider.get(), settingsRepositoryProvider.get());
  }

  public static SetupClinicUseCase_Factory create(
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new SetupClinicUseCase_Factory(serviceRepositoryProvider, settingsRepositoryProvider);
  }

  public static SetupClinicUseCase newInstance(ServiceRepository serviceRepository,
      SettingsRepository settingsRepository) {
    return new SetupClinicUseCase(serviceRepository, settingsRepository);
  }
}
