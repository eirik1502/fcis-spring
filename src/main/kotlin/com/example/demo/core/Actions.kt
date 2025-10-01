package com.example.demo.core

import com.example.demo.model.User
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

sealed interface Action {
}

abstract class ParentAction : Action {
    val children: List<Action>
    fun
}

@Service
class ActionPerformer(
    private val transactionPerformer: TransactionPerformer
) {
    fun performAction(action: Action) {
        return when (action) {
            is SaveUser -> TODO()
            is Transaction -> transactionPerformer.start(action)
        }
    }
}

@Component
class TransactionPerformer(
    private val txTemplate: TransactionTemplate
) {
    fun start(action: Transaction) {
        txTemplate.execute {

        }
    }
}

fun createNewUser(): Action {
    val user = User(
        username = "",
        firstName = "",
        lastName = "",
        email = "",
    )
    return Transaction {
        +SaveUser(
            user = user,
        ) { savedUser ->
            +PublishUserSavedEvent(user = savedUser)
        }
    }
}

class ActionBuilder {
    private val actions: MutableList<Action> = mutableListOf()
    operator fun Action.unaryPlus() {
         actions.add(this)
    }
}

data class Transaction(
    val next: ActionBuilder.(User) -> Unit
) : Action
data class SaveUser(
    val user: User,
    val next: ActionBuilder.(User) -> Unit
) : Action
data class PublishUserSavedEvent(
    val user: User
) : Action

