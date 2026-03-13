package com.cuecall.app.ui.screens.counter;

import com.cuecall.app.domain.repository.CounterRepository;
import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.repository.TokenRepository;
import com.cuecall.app.domain.usecase.CallNextTokenUseCase;
import com.cuecall.app.domain.usecase.CompleteTokenUseCase;
import com.cuecall.app.domain.usecase.RecallTokenUseCase;
import com.cuecall.app.domain.usecase.SetupValidator;
import com.cuecall.app.domain.usecase.SkipTokenUseCase;
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
public final class CounterViewModel_Factory implements Factory<CounterViewModel> {
  private final Provider<TokenRepository> tokenRepositoryProvider;

  private final Provider<QueueDayRepository> queueDayRepositoryProvider;

  private final Provider<CounterRepository> counterRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SetupValidator> setupValidatorProvider;

  private final Provider<CallNextTokenUseCase> callNextTokenUseCaseProvider;

  private final Provider<RecallTokenUseCase> recallTokenUseCaseProvider;

  private final Provider<SkipTokenUseCase> skipTokenUseCaseProvider;

  private final Provider<CompleteTokenUseCase> completeTokenUseCaseProvider;

  public CounterViewModel_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider,
      Provider<CallNextTokenUseCase> callNextTokenUseCaseProvider,
      Provider<RecallTokenUseCase> recallTokenUseCaseProvider,
      Provider<SkipTokenUseCase> skipTokenUseCaseProvider,
      Provider<CompleteTokenUseCase> completeTokenUseCaseProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
    this.counterRepositoryProvider = counterRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.setupValidatorProvider = setupValidatorProvider;
    this.callNextTokenUseCaseProvider = callNextTokenUseCaseProvider;
    this.recallTokenUseCaseProvider = recallTokenUseCaseProvider;
    this.skipTokenUseCaseProvider = skipTokenUseCaseProvider;
    this.completeTokenUseCaseProvider = completeTokenUseCaseProvider;
  }

  @Override
  public CounterViewModel get() {
    return newInstance(tokenRepositoryProvider.get(), queueDayRepositoryProvider.get(), counterRepositoryProvider.get(), serviceRepositoryProvider.get(), setupValidatorProvider.get(), callNextTokenUseCaseProvider.get(), recallTokenUseCaseProvider.get(), skipTokenUseCaseProvider.get(), completeTokenUseCaseProvider.get());
  }

  public static CounterViewModel_Factory create(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<CounterRepository> counterRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider,
      Provider<CallNextTokenUseCase> callNextTokenUseCaseProvider,
      Provider<RecallTokenUseCase> recallTokenUseCaseProvider,
      Provider<SkipTokenUseCase> skipTokenUseCaseProvider,
      Provider<CompleteTokenUseCase> completeTokenUseCaseProvider) {
    return new CounterViewModel_Factory(tokenRepositoryProvider, queueDayRepositoryProvider, counterRepositoryProvider, serviceRepositoryProvider, setupValidatorProvider, callNextTokenUseCaseProvider, recallTokenUseCaseProvider, skipTokenUseCaseProvider, completeTokenUseCaseProvider);
  }

  public static CounterViewModel newInstance(TokenRepository tokenRepository,
      QueueDayRepository queueDayRepository, CounterRepository counterRepository,
      ServiceRepository serviceRepository, SetupValidator setupValidator,
      CallNextTokenUseCase callNextTokenUseCase, RecallTokenUseCase recallTokenUseCase,
      SkipTokenUseCase skipTokenUseCase, CompleteTokenUseCase completeTokenUseCase) {
    return new CounterViewModel(tokenRepository, queueDayRepository, counterRepository, serviceRepository, setupValidator, callNextTokenUseCase, recallTokenUseCase, skipTokenUseCase, completeTokenUseCase);
  }
}
