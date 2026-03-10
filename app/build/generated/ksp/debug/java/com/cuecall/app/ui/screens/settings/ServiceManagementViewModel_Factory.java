package com.cuecall.app.ui.screens.settings;

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
public final class ServiceManagementViewModel_Factory implements Factory<ServiceManagementViewModel> {
  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public ServiceManagementViewModel_Factory(Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public ServiceManagementViewModel get() {
    return newInstance(serviceRepositoryProvider.get(), settingsRepositoryProvider.get());
  }

  public static ServiceManagementViewModel_Factory create(
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new ServiceManagementViewModel_Factory(serviceRepositoryProvider, settingsRepositoryProvider);
  }

  public static ServiceManagementViewModel newInstance(ServiceRepository serviceRepository,
      SettingsRepository settingsRepository) {
    return new ServiceManagementViewModel(serviceRepository, settingsRepository);
  }
}
