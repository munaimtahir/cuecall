package com.cuecall.app.ui.screens.counter;

import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.domain.repository.TokenRepository;
import com.cuecall.app.domain.usecase.CallNextTokenUseCase;
import com.cuecall.app.domain.usecase.CompleteTokenUseCase;
import com.cuecall.app.domain.usecase.RecallTokenUseCase;
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

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<CallNextTokenUseCase> callNextTokenUseCaseProvider;

  private final Provider<RecallTokenUseCase> recallTokenUseCaseProvider;

  private final Provider<SkipTokenUseCase> skipTokenUseCaseProvider;

  private final Provider<CompleteTokenUseCase> completeTokenUseCaseProvider;

  public CounterViewModel_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallNextTokenUseCase> callNextTokenUseCaseProvider,
      Provider<RecallTokenUseCase> recallTokenUseCaseProvider,
      Provider<SkipTokenUseCase> skipTokenUseCaseProvider,
      Provider<CompleteTokenUseCase> completeTokenUseCaseProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.callNextTokenUseCaseProvider = callNextTokenUseCaseProvider;
    this.recallTokenUseCaseProvider = recallTokenUseCaseProvider;
    this.skipTokenUseCaseProvider = skipTokenUseCaseProvider;
    this.completeTokenUseCaseProvider = completeTokenUseCaseProvider;
  }

  @Override
  public CounterViewModel get() {
    return newInstance(tokenRepositoryProvider.get(), queueDayRepositoryProvider.get(), settingsRepositoryProvider.get(), callNextTokenUseCaseProvider.get(), recallTokenUseCaseProvider.get(), skipTokenUseCaseProvider.get(), completeTokenUseCaseProvider.get());
  }

  public static CounterViewModel_Factory create(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallNextTokenUseCase> callNextTokenUseCaseProvider,
      Provider<RecallTokenUseCase> recallTokenUseCaseProvider,
      Provider<SkipTokenUseCase> skipTokenUseCaseProvider,
      Provider<CompleteTokenUseCase> completeTokenUseCaseProvider) {
    return new CounterViewModel_Factory(tokenRepositoryProvider, queueDayRepositoryProvider, settingsRepositoryProvider, callNextTokenUseCaseProvider, recallTokenUseCaseProvider, skipTokenUseCaseProvider, completeTokenUseCaseProvider);
  }

  public static CounterViewModel newInstance(TokenRepository tokenRepository,
      QueueDayRepository queueDayRepository, SettingsRepository settingsRepository,
      CallNextTokenUseCase callNextTokenUseCase, RecallTokenUseCase recallTokenUseCase,
      SkipTokenUseCase skipTokenUseCase, CompleteTokenUseCase completeTokenUseCase) {
    return new CounterViewModel(tokenRepository, queueDayRepository, settingsRepository, callNextTokenUseCase, recallTokenUseCase, skipTokenUseCase, completeTokenUseCase);
  }
}
