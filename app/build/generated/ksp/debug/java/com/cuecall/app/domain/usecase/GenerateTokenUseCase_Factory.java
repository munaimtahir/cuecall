package com.cuecall.app.domain.usecase;

import com.cuecall.app.domain.repository.CallEventRepository;
import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.domain.repository.TokenRepository;
import com.cuecall.app.domain.repository.TokenSequenceRepository;
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
public final class GenerateTokenUseCase_Factory implements Factory<GenerateTokenUseCase> {
  private final Provider<TokenRepository> tokenRepositoryProvider;

  private final Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider;

  private final Provider<QueueDayRepository> queueDayRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<CallEventRepository> callEventRepositoryProvider;

  public GenerateTokenUseCase_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.tokenSequenceRepositoryProvider = tokenSequenceRepositoryProvider;
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.callEventRepositoryProvider = callEventRepositoryProvider;
  }

  @Override
  public GenerateTokenUseCase get() {
    return newInstance(tokenRepositoryProvider.get(), tokenSequenceRepositoryProvider.get(), queueDayRepositoryProvider.get(), serviceRepositoryProvider.get(), settingsRepositoryProvider.get(), callEventRepositoryProvider.get());
  }

  public static GenerateTokenUseCase_Factory create(
      Provider<TokenRepository> tokenRepositoryProvider,
      Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider) {
    return new GenerateTokenUseCase_Factory(tokenRepositoryProvider, tokenSequenceRepositoryProvider, queueDayRepositoryProvider, serviceRepositoryProvider, settingsRepositoryProvider, callEventRepositoryProvider);
  }

  public static GenerateTokenUseCase newInstance(TokenRepository tokenRepository,
      TokenSequenceRepository tokenSequenceRepository, QueueDayRepository queueDayRepository,
      ServiceRepository serviceRepository, SettingsRepository settingsRepository,
      CallEventRepository callEventRepository) {
    return new GenerateTokenUseCase(tokenRepository, tokenSequenceRepository, queueDayRepository, serviceRepository, settingsRepository, callEventRepository);
  }
}
