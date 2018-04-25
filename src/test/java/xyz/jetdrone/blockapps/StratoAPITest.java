package xyz.jetdrone.blockapps;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(VertxUnitRunner.class)
public class StratoAPITest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private StratoAPI strato;

  @Before
  public void before() {
    strato = StratoAPI.create(rule.vertx(), "http://localhost/strato-api");
  }

  @Test
  public void getTransaction(TestContext should) {
    final Async test = should.async();
    strato.getTransaction(new JsonObject().put("minvalue", 0), res -> {
      should.assertTrue(res.succeeded());
      System.out.println(res.result().encodePrettily());
      test.complete();
    });
  }

  @Test
  public void postTransaction(TestContext should) {
  }

  @Test
  public void getLastTransaction(TestContext should) {
    final Async test = should.async();
    strato.getLastTransaction(15, res -> {
      should.assertTrue(res.succeeded());
      System.out.println(res.result().encodePrettily());
      test.complete();
    });
  }

  @Test
  public void postTransactionList(TestContext should) {
  }

  @Test
  public void getTransactionResult(TestContext should) {
  }

  @Test
  public void postTransactionBatch(TestContext should) {
  }

  @Test
  public void getBlock(TestContext should) {
  }

  @Test
  public void getLastBlock(TestContext should) {
  }

  @Test
  public void getAccount(TestContext should) {
  }

  @Test
  public void getDifficultyStats(TestContext should) {
  }

  @Test
  public void getTotalTXStats(TestContext should) {
  }

  @Test
  public void getStorage(TestContext should) {
  }

  @Test
  public void postFaucet(TestContext should) {
  }

  @Test
  public void postSolc(TestContext should) {
  }

  @Test
  public void postExtABI(TestContext should) {
  }
}
