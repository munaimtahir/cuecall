package com.cuecall.app.domain.usecase;

import com.cuecall.app.domain.repository.CallEventRepository;
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

  private final Provider<CallEventRepository> callEventRepositoryProvider;

  private final Provider<SetupValidator> setupValidatorProvider;

  public GenerateTokenUseCase_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.tokenSequenceRepositoryProvider = tokenSequenceRepositoryProvider;
    this.callEventRepositoryProvider = callEventRepositoryProvider;
    this.setupValidatorProvider = setupValidatorProvider;
  }

  @Override
  public GenerateTokenUseCase get() {
    return newInstance(tokenRepositoryProvider.get(), tokenSequenceRepositoryProvider.get(), callEventRepositoryProvider.get(), setupValidatorProvider.get());
  }

  public static GenerateTokenUseCase_Factory create(
      Provider<TokenRepository> tokenRepositoryProvider,
      Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider,
      Provider<CallEventRepository> callEventRepositoryProvider,
      Provider<SetupValidator> setupValidatorProvider) {
    return new GenerateTokenUseCase_Factory(tokenRepositoryProvider, tokenSequenceRepositoryProvider, callEventRepositoryProvider, setupValidatorProvider);
  }

  public static GenerateTokenUseCase newInstance(TokenRepository tokenRepository,
      TokenSequenceRepository tokenSequenceRepository, CallEventRepository callEventRepository,
      SetupValidator setupValidator) {
    return new GenerateTokenUseCase(tokenRepository, tokenSequenceRepository, callEventRepository, setupValidator);
  }
}
