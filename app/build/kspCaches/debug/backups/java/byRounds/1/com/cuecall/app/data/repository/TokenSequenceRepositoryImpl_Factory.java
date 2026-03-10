package com.cuecall.app.data.repository;

import com.cuecall.app.data.remote.source.FirestoreTokenSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class TokenSequenceRepositoryImpl_Factory implements Factory<TokenSequenceRepositoryImpl> {
  private final Provider<FirestoreTokenSource> firestoreSourceProvider;

  public TokenSequenceRepositoryImpl_Factory(
      Provider<FirestoreTokenSource> firestoreSourceProvider) {
    this.firestoreSourceProvider = firestoreSourceProvider;
  }

  @Override
  public TokenSequenceRepositoryImpl get() {
    return newInstance(firestoreSourceProvider.get());
  }

  public static TokenSequenceRepositoryImpl_Factory create(
      Provider<FirestoreTokenSource> firestoreSourceProvider) {
    return new TokenSequenceRepositoryImpl_Factory(firestoreSourceProvider);
  }

  public static TokenSequenceRepositoryImpl newInstance(FirestoreTokenSource firestoreSource) {
    return new TokenSequenceRepositoryImpl(firestoreSource);
  }
}
