package com.cuecall.app.ui.screens.reception;

import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.domain.usecase.GenerateTokenUseCase;
import com.cuecall.app.printer.PrinterManager;
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
public final class ReceptionViewModel_Factory implements Factory<ReceptionViewModel> {
  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<GenerateTokenUseCase> generateTokenUseCaseProvider;

  private final Provider<PrinterManager> printerManagerProvider;

  public ReceptionViewModel_Factory(Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<GenerateTokenUseCase> generateTokenUseCaseProvider,
      Provider<PrinterManager> printerManagerProvider) {
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.generateTokenUseCaseProvider = generateTokenUseCaseProvider;
    this.printerManagerProvider = printerManagerProvider;
  }

  @Override
  public ReceptionViewModel get() {
    return newInstance(serviceRepositoryProvider.get(), settingsRepositoryProvider.get(), generateTokenUseCaseProvider.get(), printerManagerProvider.get());
  }

  public static ReceptionViewModel_Factory create(
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<GenerateTokenUseCase> generateTokenUseCaseProvider,
      Provider<PrinterManager> printerManagerProvider) {
    return new ReceptionViewModel_Factory(serviceRepositoryProvider, settingsRepositoryProvider, generateTokenUseCaseProvider, printerManagerProvider);
  }

  public static ReceptionViewModel newInstance(ServiceRepository serviceRepository,
      SettingsRepository settingsRepository, GenerateTokenUseCase generateTokenUseCase,
      PrinterManager printerManager) {
    return new ReceptionViewModel(serviceRepository, settingsRepository, generateTokenUseCase, printerManager);
  }
}
