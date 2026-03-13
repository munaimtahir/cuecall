package com.cuecall.app.ui.screens.reception;

import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.usecase.GenerateTokenUseCase;
import com.cuecall.app.domain.usecase.SetupValidator;
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

  private final Provider<GenerateTokenUseCase> generateTokenUseCaseProvider;

  private final Provider<PrinterManager> printerManagerProvider;

  private final Provider<SetupValidator> setupValidatorProvider;

  public ReceptionViewModel_Factory(Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<GenerateTokenUseCase> generateTokenUseCaseProvider,
      Provider<PrinterManager> printerManagerProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.generateTokenUseCaseProvider = generateTokenUseCaseProvider;
    this.printerManagerProvider = printerManagerProvider;
    this.setupValidatorProvider = setupValidatorProvider;
  }

  @Override
  public ReceptionViewModel get() {
    return newInstance(serviceRepositoryProvider.get(), generateTokenUseCaseProvider.get(), printerManagerProvider.get(), setupValidatorProvider.get());
  }

  public static ReceptionViewModel_Factory create(
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<GenerateTokenUseCase> generateTokenUseCaseProvider,
      Provider<PrinterManager> printerManagerProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    return new ReceptionViewModel_Factory(serviceRepositoryProvider, generateTokenUseCaseProvider, printerManagerProvider, setupValidatorProvider);
  }

  public static ReceptionViewModel newInstance(ServiceRepository serviceRepository,
      GenerateTokenUseCase generateTokenUseCase, PrinterManager printerManager,
      SetupValidator setupValidator) {
    return new ReceptionViewModel(serviceRepository, generateTokenUseCase, printerManager, setupValidator);
  }
}
