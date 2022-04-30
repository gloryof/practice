import logging


def pytest_tavern_beta_before_severy_test_run(test_dict, variables):
    logging.info("test")
