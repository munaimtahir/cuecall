package com.cuecall.app.domain.usecase;

import com.cuecall.app.domain.repository.CallEventRepository;
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
public final class SkipTokenUseCase_Factory implements Factory<SkipTokenUseCase> {
  private final Provider<TokenRepository> tokenRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<CallEventRepository> callEventRepositoryProvider;

  public SkipTokenUseCase_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.callEventRepositoryProvider = callEventRepositoryProvider;
  }

  @Override
  public SkipTokenUseCase get() {
    return newInstance(tokenRepositoryProvider.get(), settingsRepositoryProvider.get(), callEventRepositoryProvider.get());
  }

  public static SkipTokenUseCase_Factory create(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider) {
    return new SkipTokenUseCase_Factory(tokenRepositoryProvider, settingsRepositoryProvider, callEventRepositoryProvider);
  }

  public static SkipTokenUseCase newInstance(TokenRepository tokenRepository,
      SettingsRepository settingsRepository, CallEventRepository callEventRepository) {
    return new SkipTokenUseCase(tokenRepository, settingsRepository, callEventRepository);
  }
}
