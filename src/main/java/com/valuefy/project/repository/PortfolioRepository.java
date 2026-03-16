package com.valuefy.project.repository;

import com.valuefy.project.dto.RebalanceItemDTO;
import com.valuefy.project.model.ClientHolding;
import com.valuefy.project.model.ModelFund;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PortfolioRepository {

    private final JdbcTemplate jdbc;

    public List<ClientHolding> getClientHoldings(String clientId) {

        String sql = """
            SELECT holding_id, client_id, fund_id, fund_name, current_value
            FROM _client_holdings
            WHERE client_id = ?
        """;

        return jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(ClientHolding.class),
                clientId
        );
    }

    public List<ModelFund> getModelFunds() {

        String sql = """
            SELECT fund_id, fund_name, asset_class, allocation_pct
            FROM _model_funds
        """;

        return jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(ModelFund.class)
        );
    }

    public void upsertModelFund(ModelFund fund) {

        String sql = """
        INSERT INTO _model_funds (fund_id, fund_name, asset_class, allocation_pct)
        VALUES (?, ?, ?, ?)
        ON CONFLICT (fund_id)
        DO UPDATE SET
            fund_name = EXCLUDED.fund_name,
            asset_class = EXCLUDED.asset_class,
            allocation_pct = EXCLUDED.allocation_pct
    """;

        jdbc.update(sql,
                fund.getFundId(),
                fund.getFundName(),
                fund.getAssetClass(),
                fund.getAllocationPct());
    }
}