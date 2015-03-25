package com.monitorjbl;

import com.google.gson.Gson;
import com.monitorjbl.model.Customer;
import com.monitorjbl.model.LineItem;
import com.monitorjbl.model.Receipt;
import com.monitorjbl.persistence.CustomerRepo;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Lists.newArrayList;

public class Main {
  @Autowired
  private CustomerRepo repo;
  @Autowired
  private EntityManager em;

  @Transactional
  public Customer insert() {
    Customer c = repo.save(Customer.builder()
        .name("Strickland Propane")
        .billingLevel(Customer.BillingLevel.TRIAL)
        .account(UUID.randomUUID().toString())
        .contacts(Arrays.asList("Hank", "Joe-Jack"))
        .receipts(Arrays.asList(Receipt.builder()
            .date(new Date())
            .lineItems(Arrays.asList(
                LineItem.builder()
                    .name("Burgers")
                    .value(12.50)
                    .build(),
                LineItem.builder()
                    .name("Fries")
                    .value(6.75)
                    .build()
            ))
            .build()))
        .build());

    c = repo.findById(c.getId());
    return c;
  }

  @Transactional
  public Customer update(Long id) {
    Customer c = repo.findById(id);

    Receipt r = getOnlyElement(c.getReceipts());
    List<LineItem> lineItems = newArrayList();
    for (LineItem li : r.getLineItems()) {
      lineItems.add(li.copyBuilder()
          .name("Vegan-" + li.getName())
          .value(li.getValue() * 1.75)
          .build());
    }

    c = c.copyBuilder()
        .name("Hill Propane")
        .billingLevel(Customer.BillingLevel.PREMIUM)
        .contacts(newArrayList("Peggy", "Bobby"))
        .receipts(newArrayList(r.copyBuilder().lineItems(lineItems).build()))
        .build();

    System.out.println(new Gson().toJson(c));
    return repo.save(c);
  }

  @Transactional
  public Customer getCustomerAtVersion(Long customerId, Number revisionId) {
    AuditReader reader = AuditReaderFactory.get(em);
    Customer rev = reader.find(Customer.class, customerId, revisionId);
    System.out.println("rev" + revisionId + ": " + new Gson().toJson(rev));
    return rev;
  }

  @Transactional
  public List<Number> getRevisions(Long id) {
    AuditReader reader = AuditReaderFactory.get(em);
    return reader.getRevisions(Customer.class, id);
  }

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class, Context.class);
    Main main = context.getBean(Main.class);

    Customer original = main.insert();
    Customer updated = main.update(original.getId());

    List<Number> revs = main.getRevisions(original.getId());
    for (Number r : revs) {
      main.getCustomerAtVersion(original.getId(), r);
    }
  }
}
