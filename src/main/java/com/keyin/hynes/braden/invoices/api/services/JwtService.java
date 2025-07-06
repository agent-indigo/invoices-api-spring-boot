package com.keyin.hynes.braden.invoices.api.services;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public final class JwtService {
  private SecretKey getSecretKey() {
    try {
      return Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(
          Base64.getEncoder().encodeToString(
            KeyGenerator.getInstance(
              "HmacSHA256"
            ).generateKey().getEncoded()
          )
        )
      );
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new RuntimeException(
        "HmacSHA256 algorithm not available.",
        noSuchAlgorithmException
      );
    }
  }
  public String generateJwt(final UUID id) {
    return Jwts.builder().claims().add(
      new HashMap<String, Object>()
    ).id(
      UUID.randomUUID().toString()
    ).issuer(
      "invoices.api"
    ).audience().add(
      "invoices.client"
    ).and().subject(
      id.toString()
    ).issuedAt(
      new Date(
        System.currentTimeMillis() - 30
      )
    ).notBefore(
      new Date(
        System.currentTimeMillis() - 30
      )
    ).expiration(
      new Date(
        System.currentTimeMillis() + 2592030 // 30 days
      )
    ).and().signWith(
      getSecretKey()
    ).compact();
  }
  private <T> T getClaim(
    final String jwt,
    final Function<Claims, T> claimsResolver
  ) {
    return claimsResolver.apply(Jwts.parser().verifyWith(
      getSecretKey()
    ).build().parseSignedClaims(
      jwt
    ).getPayload());
  }
  public UUID getUserId(final String jwt) {
    return UUID.fromString(getClaim(
      jwt,
      Claims::getSubject
    ));
  }
  public boolean isJwtValid(final String jwt) {
    return getClaim(
      jwt,
      Claims::getNotBefore
    ).before(new Date(System.currentTimeMillis())) && getClaim(
      jwt,
      Claims::getExpiration
    ).after(new Date(System.currentTimeMillis())) && getClaim(
      jwt,
      Claims::getIssuer
    ).equals("invoices.api") && getClaim(
      jwt,
      Claims::getAudience
    ).contains("invoices.client");
  }
}