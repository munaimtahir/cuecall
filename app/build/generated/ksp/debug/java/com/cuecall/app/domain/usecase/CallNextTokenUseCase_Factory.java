package com.cuecall.app.domain.usecase;

import com.cuecall.app.domain.repository.CallEventRepository;
import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.domain.repository.TokenRepository;
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
public final class CallNextTokenUseCase_Factory implements Factory<CallNextTokenUseCase> {
  private final Provider<TokenRepository> tokenRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<CallEventRepository> callEventRepositoryProvider;

  private final Provider<QueueDayRepository> queueDayRepositoryProvider;

  public CallNextTokenUseCase_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.callEventRepositoryProvider = callEventRepositoryProvider;
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
  }

  @Override
  public CallNextTokenUseCase get() {
    return newInstance(tokenRepositoryProvider.get(), settingsRepositoryProvider.get(), callEventRepositoryProvider.get(), queueDayRepositoryProvider.get());
  }

  public static CallNextTokenUseCase_Factory create(
      Provider<TokenRepository> tokenRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider) {
    return new CallNextTokenUseCase_Factory(tokenRepositoryProvider, settingsRepositoryProvider, callEventRepositoryProvider, queueDayRepositoryProvider);
  }

  public static CallNextTokenUseCase newInstance(TokenRepository tokenRepository,
      SettingsRepository settingsRepository, CallEventRepository callEventRepository,
      QueueDayRepository queueDayRepository) {
    return new CallNextTokenUseCase(tokenRepository, settingsRepository, callEventRepository, queueDayRepository);
  }
}
